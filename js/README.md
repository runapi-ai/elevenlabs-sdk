# ElevenLabs JavaScript SDK for RunAPI

The ElevenLabs JavaScript SDK is the language-specific package for ElevenLabs on RunAPI. Use this package for voice, dialogue, transcription, sound effect, and audio cleanup workflows when your application needs request bodies, task status lookup, and consistent RunAPI errors in JavaScript.

This README is the JavaScript package guide inside the public `elevenlabs-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/elevenlabs; for API reference, use https://runapi.ai/docs/api/elevenlabs/text-to-speech; for SDK docs, use https://runapi.ai/docs/resources/sdks.

## Install

```bash
npm install @runapi.ai/elevenlabs
```

## Quick start

```typescript
import { ElevenlabsClient } from '@runapi.ai/elevenlabs';

const client = new ElevenlabsClient();
const task = await client.textToSpeech.create({
  // Pass the ElevenLabs JSON request body from https://runapi.ai/docs/api/elevenlabs/text-to-speech.
});
const status = await client.textToSpeech.get(task.id);
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Use the TypeScript types in `src/types.ts` and the resource classes under `src/resources` when building audio applications. The available resources are `textToSpeech`, `textToDialogue`, `textToSound`, `speechToText`, and `isolateAudio`. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

## Links

- Model page: https://runapi.ai/models/elevenlabs
- SDK docs: https://runapi.ai/docs/resources/sdks
- Product docs: https://runapi.ai/docs/api/elevenlabs/text-to-speech
- Pricing and rate limits: https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5
- Provider comparison: https://runapi.ai/providers/elevenlabs
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/elevenlabs-sdk

## License

Licensed under the Apache License, Version 2.0.
