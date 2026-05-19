import type { AsyncTaskStatus } from '@runapi.ai/core';

export type ElevenlabsSpeechModel =
  | 'text-to-speech-turbo-2-5'
  | 'text-to-speech-multilingual-v2';

export type ElevenlabsSoundEffectOutputFormat =
  | 'mp3_22050_32'
  | 'mp3_44100_32'
  | 'mp3_44100_64'
  | 'mp3_44100_96'
  | 'mp3_44100_128'
  | 'mp3_44100_192'
  | 'pcm_8000'
  | 'pcm_16000'
  | 'pcm_22050'
  | 'pcm_24000'
  | 'pcm_44100'
  | 'pcm_48000'
  | 'ulaw_8000'
  | 'alaw_8000'
  | 'opus_48000_32'
  | 'opus_48000_64'
  | 'opus_48000_96'
  | 'opus_48000_128'
  | 'opus_48000_192';

export interface TaskCreateResponse {
  id: string;
  status?: AsyncTaskStatus;
}

export interface AudioFile {
  url: string;
}

export interface AudioTaskResponse {
  id: string;
  status: AsyncTaskStatus;
  audios?: AudioFile[];
  error?: string;
  [key: string]: unknown;
}

export interface TextToSpeechParams {
  model: ElevenlabsSpeechModel;
  text: string;
  voice?: string;
  callback_url?: string;
  stability?: number;
  similarity_boost?: number;
  style?: number;
  speed?: number;
  timestamps?: boolean;
  previous_text?: string;
  next_text?: string;
  language_code?: string;
}

export interface DialogueLine {
  text: string;
  voice: string;
}

export interface TextToDialogueParams {
  dialogue: DialogueLine[];
  callback_url?: string;
  stability?: 0 | 0.5 | 1;
  language_code?: string;
}

export interface TextToSoundParams {
  text: string;
  callback_url?: string;
  loop?: boolean;
  duration_seconds?: number;
  prompt_influence?: number;
  output_format?: ElevenlabsSoundEffectOutputFormat;
}

export interface SpeechToTextParams {
  audio_url: string;
  callback_url?: string;
  language_code?: string;
  tag_audio_events?: boolean;
  diarize?: boolean;
}

export interface SpeechToTextResponse {
  id: string;
  status: AsyncTaskStatus;
  text?: string;
  error?: string;
  [key: string]: unknown;
}

export interface IsolateAudioParams {
  audio_url: string;
  callback_url?: string;
}

/**
 * Resolved responses returned by the `run()` methods after polling sees
 * `status: 'completed'`. Narrows the base response so result fields
 * (`audios` / `text`) are guaranteed non-optional in user code.
 */
export type CompletedAudioTaskResponse = AudioTaskResponse & {
  status: 'completed';
  audios: AudioFile[];
};

export type CompletedSpeechToTextResponse = SpeechToTextResponse & {
  status: 'completed';
  text: string;
};
