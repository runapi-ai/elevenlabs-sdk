import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { TextToSpeech } from './resources/text-to-speech';
import { TextToDialogue } from './resources/text-to-dialogue';
import { TextToSound } from './resources/text-to-sound';
import { SpeechToText } from './resources/speech-to-text';
import { IsolateAudio } from './resources/isolate-audio';

/**
 * ElevenLabs audio API client for speech synthesis, multi-speaker dialogue,
 * sound effects, transcription, and vocal isolation.
 *
 * @example
 * ```typescript
 * import { ElevenlabsClient } from '@runapi.ai/elevenlabs';
 * const client = new ElevenlabsClient({ apiKey: 'sk-...' });
 * const result = await client.textToSpeech.run({
 *   model: 'text-to-speech-turbo-v2.5',
 *   text: 'Hello, world!',
 * });
 * console.log(result.audios[0].url);
 * ```
 */
export class ElevenlabsClient extends BaseClient {
  /** Single-speaker speech synthesis with configurable voice, speed, and language. */
  public readonly textToSpeech: TextToSpeech;
  /** Multi-speaker dialogue where each line can use a different voice. */
  public readonly textToDialogue: TextToDialogue;
  /** Sound effect generation (rain, footsteps, ambience) from text descriptions. */
  public readonly textToSound: TextToSound;
  /** Audio transcription with optional speaker diarization and audio event tagging. */
  public readonly speechToText: SpeechToText;
  /** Vocal isolation that separates speech from background noise. */
  public readonly isolateAudio: IsolateAudio;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.textToSpeech = new TextToSpeech(this.http);
    this.textToDialogue = new TextToDialogue(this.http);
    this.textToSound = new TextToSound(this.http);
    this.speechToText = new SpeechToText(this.http);
    this.isolateAudio = new IsolateAudio(this.http);
  }
}
