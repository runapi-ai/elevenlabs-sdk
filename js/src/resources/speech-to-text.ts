import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { CompletedSpeechToTextResponse, TaskCreateResponse, SpeechToTextParams, SpeechToTextResponse } from '../types';

const ENDPOINT = '/api/v1/elevenlabs/speech_to_text';

/**
 * Audio transcription with optional speaker diarization and audio event tagging
 * (laughter, applause, music).
 */
export class SpeechToText {
  constructor(private readonly http: HttpClient) {}

  /**
   * Transcribe audio and wait until complete.
   * @param params Transcription parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed transcription result.
   */
  async run(params: SpeechToTextParams, options?: RequestOptions & PollingOptions): Promise<CompletedSpeechToTextResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<SpeechToTextResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedSpeechToTextResponse;
  }

  /**
   * Create a transcription task; returns immediately with a task id.
   * @param params Transcription parameters.
   * @param options Per-request overrides.
   * @returns The task creation result with id.
   */
  async create(params: SpeechToTextParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of a transcription task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current transcription status.
   */
  async get(id: string, options?: RequestOptions): Promise<SpeechToTextResponse> {
    return this.http.request<SpeechToTextResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
