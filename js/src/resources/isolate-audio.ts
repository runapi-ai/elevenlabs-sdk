import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { IsolateAudioParams, AudioTaskResponse, CompletedAudioTaskResponse, TaskCreateResponse } from '../types';

const ENDPOINT = '/api/v1/elevenlabs/isolate_audio';

/**
 * Vocal isolation that separates speech from background noise,
 * returning a clean vocal-only audio track.
 */
export class IsolateAudio {
  constructor(private readonly http: HttpClient) {}

  /**
   * Isolate vocals and wait until complete.
   * @param params Vocal isolation parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed audio task with results.
   */
  async run(params: IsolateAudioParams, options?: RequestOptions & PollingOptions): Promise<CompletedAudioTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<AudioTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedAudioTaskResponse;
  }

  /**
   * Create a vocal isolation task; returns immediately with a task id.
   * @param params Vocal isolation parameters.
   * @param options Per-request overrides.
   * @returns The task creation result with id.
   */
  async create(params: IsolateAudioParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of a vocal isolation task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current audio task status.
   */
  async get(id: string, options?: RequestOptions): Promise<AudioTaskResponse> {
    return this.http.request<AudioTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
