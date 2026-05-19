import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { TextToSpeech } from '../../src/resources/text-to-speech';
import { TextToDialogue } from '../../src/resources/text-to-dialogue';
import { TextToSound } from '../../src/resources/text-to-sound';
import { SpeechToText } from '../../src/resources/speech-to-text';
import { IsolateAudio } from '../../src/resources/isolate-audio';

describe('Elevenlabs resources', () => {
  const mockHttp: HttpClient = {
    request: vi.fn(),
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('creates textToSpeech with flat params', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-1' });
    const textToSpeech = new TextToSpeech(mockHttp);

    await textToSpeech.create({
      model: 'text-to-speech-turbo-2-5',
      text: 'Hello',
      voice: 'Rachel',
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/elevenlabs/text_to_speech', {
      body: {
        model: 'text-to-speech-turbo-2-5',
        text: 'Hello',
        voice: 'Rachel',
      },
    });
  });

  it('creates textToDialogue with nested dialogue items', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-2' });
    const textToDialogue = new TextToDialogue(mockHttp);

    await textToDialogue.create({
      dialogue: [{ text: 'Hello', voice: 'Adam' }],
      stability: 0.5,
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/elevenlabs/text_to_dialogue', {
      body: {
        dialogue: [{ text: 'Hello', voice: 'Adam' }],
        stability: 0.5,
      },
    });
  });

  it('creates text-to-sound tasks with output format', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-3' });
    const textToSound = new TextToSound(mockHttp);

    await textToSound.create({ text: 'Thunder crash', output_format: 'mp3_44100_128' });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/elevenlabs/text_to_sound', {
      body: {
        text: 'Thunder crash',
        output_format: 'mp3_44100_128',
      },
    });
  });

  it('gets speechToText by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-4', status: 'completed', text: 'Hello' });
    const speechToText = new SpeechToText(mockHttp);

    const result = await speechToText.get('task-4');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/elevenlabs/speech_to_text/task-4', {});
    expect(result.text).toBe('Hello');
  });

  it('gets isolate-audio tasks by id', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({ id: 'task-5', status: 'completed', audios: [{ url: 'https://file.runapi.ai/audio.mp3' }] });
    const isolateAudio = new IsolateAudio(mockHttp);

    const result = await isolateAudio.get('task-5');

    expect(mockHttp.request).toHaveBeenCalledWith('GET', '/api/v1/elevenlabs/isolate_audio/task-5', {});
    expect(result.audios?.[0]?.url).toBe('https://file.runapi.ai/audio.mp3');
  });
});
