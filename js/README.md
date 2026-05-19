# Elevenlabs API JavaScript SDK for RunAPI

The elevenlabs api JavaScript SDK is the language-specific package for ElevenLabs on RunAPI. Use this elevenlabs api package for voice, dialogue, transcription, sound effect, and cleanup flows when your application needs JSON request bodies, task status lookup, and consistent RunAPI errors in JavaScript.

This elevenlabs api README is the JavaScript package guide inside the public `elevenlabs-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/elevenlabs; for API reference, use https://runapi.ai/docs#elevenlabs; for SDK docs, use https://runapi.ai/docs#sdk-elevenlabs.

## Install

```bash
npm install @runapi.ai/elevenlabs
```

## Quick start

```typescript
import { ElevenLabsClient } from '@runapi.ai/elevenlabs';

const client = new ElevenLabsClient();
const task = await client.speeches.create({
  // Pass the ElevenLabs JSON request body from https://runapi.ai/docs#elevenlabs.
});
const status = await client.speeches.get(task.id);
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion. In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

## Language notes

Use the TypeScript types in `src/types.ts` and the resource classes under `src/resources` when building audio applications. The available resources include speeches, dialogues, sound effects, transcriptions, and audio isolations. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

## Links

- Model page: https://runapi.ai/models/elevenlabs
- SDK docs: https://runapi.ai/docs#sdk-elevenlabs
- Product docs: https://runapi.ai/docs#elevenlabs
- Pricing and rate limits: https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5
- Provider comparison: https://runapi.ai/providers/elevenlabs
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/elevenlabs-sdk

## License

Licensed under the Apache License, Version 2.0.
