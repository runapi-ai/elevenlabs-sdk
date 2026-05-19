import { createHttpClient, type ClientOptions } from '@runapi.ai/core';
import { TextToSpeech } from './resources/text-to-speech';
import { TextToDialogue } from './resources/text-to-dialogue';
import { TextToSound } from './resources/text-to-sound';
import { SpeechToText } from './resources/speech-to-text';
import { IsolateAudio } from './resources/isolate-audio';

export class ElevenlabsClient {
  public readonly textToSpeech: TextToSpeech;
  public readonly textToDialogue: TextToDialogue;
  public readonly textToSound: TextToSound;
  public readonly speechToText: SpeechToText;
  public readonly isolateAudio: IsolateAudio;

  constructor(options: ClientOptions = {}) {
    const http = createHttpClient(options);
    this.textToSpeech = new TextToSpeech(http);
    this.textToDialogue = new TextToDialogue(http);
    this.textToSound = new TextToSound(http);
    this.speechToText = new SpeechToText(http);
    this.isolateAudio = new IsolateAudio(http);
  }
}
