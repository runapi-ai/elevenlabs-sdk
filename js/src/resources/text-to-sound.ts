import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { AudioTaskResponse, CompletedAudioTaskResponse, TextToSoundParams, TaskCreateResponse } from '../types';

const ENDPOINT = '/api/v1/elevenlabs/text_to_sound';

/**
 * Sound effect generation from text descriptions (rain, footsteps, ambience),
 * with configurable duration and looping.
 */
export class TextToSound {
  constructor(private readonly http: HttpClient) {}

  /**
   * Generate a sound effect and wait until complete.
   * @param params Sound effect parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed audio task with results.
   */
  async run(params: TextToSoundParams, options?: RequestOptions & PollingOptions): Promise<CompletedAudioTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<AudioTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedAudioTaskResponse;
  }

  /**
   * Create a sound effect task; returns immediately with a task id.
   * @param params Sound effect parameters.
   * @param options Per-request overrides.
   * @returns The task creation result with id.
   */
  async create(params: TextToSoundParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of a sound effect task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current audio task status.
   */
  async get(id: string, options?: RequestOptions): Promise<AudioTaskResponse> {
    return this.http.request<AudioTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
