import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { CompletedSpeechToTextResponse, TaskCreateResponse, SpeechToTextParams, SpeechToTextResponse } from '../types';

const ENDPOINT = '/api/v1/elevenlabs/speech_to_text';

export class SpeechToText {
  constructor(private readonly http: HttpClient) {}

  async run(params: SpeechToTextParams, options?: RequestOptions & PollingOptions): Promise<CompletedSpeechToTextResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<SpeechToTextResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedSpeechToTextResponse;
  }

  async create(params: SpeechToTextParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  async get(id: string, options?: RequestOptions): Promise<SpeechToTextResponse> {
    return this.http.request<SpeechToTextResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
