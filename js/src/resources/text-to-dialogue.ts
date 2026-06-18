import type { HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import type { AudioTaskResponse, CompletedAudioTaskResponse, TextToDialogueParams, TaskCreateResponse } from '../types';

const ENDPOINT = '/api/v1/elevenlabs/text_to_dialogue';

/**
 * Multi-speaker dialogue synthesis where each line can use a different voice.
 */
export class TextToDialogue {
  constructor(private readonly http: HttpClient) {}

  /**
   * Synthesize dialogue and wait until complete.
   * @param params Dialogue synthesis parameters.
   * @param options Per-request and polling overrides.
   * @returns The completed audio task with results.
   */
  async run(params: TextToDialogueParams, options?: RequestOptions & PollingOptions): Promise<CompletedAudioTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<AudioTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedAudioTaskResponse;
  }

  /**
   * Create a dialogue synthesis task; returns immediately with a task id.
   * @param params Dialogue synthesis parameters.
   * @param options Per-request overrides.
   * @returns The task creation result with id.
   */
  async create(params: TextToDialogueParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, {
      body: compactParams(params),
      ...options,
    });
  }

  /**
   * Fetch the current status of a dialogue synthesis task.
   * @param id The task id.
   * @param options Per-request overrides.
   * @returns The current audio task status.
   */
  async get(id: string, options?: RequestOptions): Promise<AudioTaskResponse> {
    return this.http.request<AudioTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
