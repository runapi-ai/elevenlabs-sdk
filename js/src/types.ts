import type { AsyncTaskStatus, TaskResponse } from '@runapi.ai/core';

/**
 * Speech synthesis model.
 * - `text-to-speech-turbo-v2.5` -- low-latency; voice is optional (uses a preset).
 * - `text-to-speech-multilingual-v2` -- 29-language support; voice is required.
 */
export type ElevenlabsSpeechModel =
  | 'text-to-speech-turbo-v2.5'
  | 'text-to-speech-multilingual-v2';

/**
 * Audio encoding for sound effect output.
 * Format is `{codec}_{sampleRateHz}_{bitrateKbps}` for lossy codecs,
 * `{codec}_{sampleRateHz}` for lossless (PCM/u-law/A-law).
 */
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

/** Acknowledgement returned by `create()` before the task starts processing. */
export interface TaskCreateResponse {
  id: string;
  status?: AsyncTaskStatus;
}

/** URL to a generated audio file. */
export interface AudioFile {
  url: string;
}

/** Result of speech, dialogue, sound effect, or vocal isolation tasks. */
export interface AudioTaskResponse extends TaskResponse {
  id: string;
  status: AsyncTaskStatus;
  /** Generated audio files; populated once the task completes. */
  audios?: AudioFile[];
  error?: string;
  [key: string]: unknown;
}

/**
 * Parameters for single-speaker speech generation.
 * Voice is optional for `text-to-speech-turbo-v2.5` (uses a preset) but
 * required for `text-to-speech-multilingual-v2`.
 */
export interface TextToSpeechParams {
  model: ElevenlabsSpeechModel;
  /** Input text to synthesize; max 5 000 characters. */
  text: string;
  /**
   * Voice name or voice ID. Required for text-to-speech-multilingual-v2;
   * optional for text-to-speech-turbo-v2.5, which defaults to
   * EkK5I93UQWFDigLMpZcX when omitted.
   */
  voice?: string;
  callback_url?: string;
  /** Voice consistency; higher values reduce variation between generations. */
  stability?: number;
  /** Voice clarity and similarity boost; 0--1. */
  similarity_boost?: number;
  /** Stylistic intensity of the voice preset. */
  style?: number;
  /** Playback speed multiplier; 0.7--1.2. */
  speed?: number;
  /** When true, the response includes word-level timestamps. */
  timestamps?: boolean;
  /** Text immediately before this segment; improves cross-segment prosody (max 5 000 chars). */
  previous_text?: string;
  /** Text immediately after this segment; improves cross-segment prosody (max 5 000 chars). */
  next_text?: string;
  /** BCP-47 language code hint (e.g. "en", "ja"). */
  language_code?: string;
}

/** A single speaker turn in a multi-speaker dialogue. */
export interface DialogueLine {
  /** Text for this speaker to say. */
  text: string;
  /** Voice name or voice ID for this line. */
  voice: string;
}

/**
 * Parameters for multi-speaker dialogue synthesis.
 * Each {@link DialogueLine} can use a different voice.
 */
export interface TextToDialogueParams {
  dialogue: DialogueLine[];
  callback_url?: string;
  /** Voice consistency; accepts 0, 0.5, or 1. */
  stability?: 0 | 0.5 | 1;
  /** BCP-47 language code hint. */
  language_code?: string;
}

/**
 * Parameters for sound effect generation (not speech).
 * Duration is 0.5--22 seconds. Set `loop` for seamless looping audio.
 */
export interface TextToSoundParams {
  /** Descriptive prompt for the desired sound (e.g. "heavy rain on a tin roof"). */
  text: string;
  callback_url?: string;
  /** Generate a seamlessly loopable audio clip. */
  loop?: boolean;
  /** Target duration in seconds; 0.5--22. */
  duration_seconds?: number;
  /** How closely the output follows the prompt; 0--1. */
  prompt_influence?: number;
  output_format?: ElevenlabsSoundEffectOutputFormat;
}

/**
 * Parameters for audio transcription.
 * Enable `diarize` to label distinct speakers; enable `tag_audio_events`
 * to annotate non-speech sounds (laughter, applause, music).
 */
export interface SpeechToTextParams {
  /** Public URL of the audio file to transcribe. */
  source_audio_url: string;
  callback_url?: string;
  /** BCP-47 language code hint to improve accuracy. */
  language_code?: string;
  /** Annotate non-speech audio events (laughter, applause, etc.) in the transcript. */
  tag_audio_events?: boolean;
  /** Label distinct speakers in the transcript. */
  diarize?: boolean;
}

/** Transcription result; `text` is populated once the task completes. */
export interface SpeechToTextResponse extends TaskResponse {
  id: string;
  status: AsyncTaskStatus;
  text?: string;
  error?: string;
  [key: string]: unknown;
}

/** Parameters for vocal isolation. Accepts only the source audio URL. */
export interface IsolateAudioParams {
  /** Public URL of the audio file to process. */
  source_audio_url: string;
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
