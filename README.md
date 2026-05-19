# ElevenLabs API SDK for RunAPI

The elevenlabs api SDK packages JavaScript, Ruby, and Go clients for ElevenLabs on RunAPI. Use this elevenlabs api SDK for text-to-speech, dialogue generation, sound effects, speech transcription, and audio isolation workflows that need typed installs, JSON request bodies, task polling, and consistent RunAPI errors across services.

ElevenLabs belongs to the ElevenLabs catalog on RunAPI. The public model page is https://runapi.ai/models/elevenlabs; variant pages below carry pricing, rate-limit, and commercial-usage details. The public `elevenlabs-sdk` repository groups the JavaScript, Ruby, and Go packages for this model.

## Install

```bash
npm install @runapi.ai/elevenlabs
gem install runapi-elevenlabs
go get github.com/runapi-ai/elevenlabs-sdk/go@latest
```

## What you can build

- Build creative tools, agent pipelines, and production integrations with the elevenlabs api SDK.
- Keep one model-specific repository while installing only the language package your app needs.
- Use `create` for submit-only jobs, `get` for status lookup, and `run` for submit-and-poll scripts.
- Handle authentication, validation, rate limits, insufficient credits, task failures, and polling timeouts through RunAPI SDK errors.

The JavaScript client exposes text to speech, text to dialogue, text to sound, speech to text, audio isolation resources, and the Ruby and Go packages mirror the same RunAPI task lifecycle.

## JavaScript quick start

```typescript
import { ElevenlabsClient } from '@runapi.ai/elevenlabs';

const client = new ElevenlabsClient();

const task = await client.textToSpeech.create({
  // Pass the ElevenLabs request body documented at https://runapi.ai/docs#elevenlabs.
});

const status = await client.textToSpeech.get(task.id);
```

For short scripts, use `run` with the same JSON body to create the task and wait for completion. For web request handlers, prefer `create` plus webhook or later `get` polling so the server does not hold a worker open.

## Repository layout

- `js/` publishes `@runapi.ai/elevenlabs`.
- `ruby/` publishes `runapi-elevenlabs` when RubyGems publishing resumes.
- `go/` publishes `github.com/runapi-ai/elevenlabs-sdk/go` and depends on `github.com/runapi-ai/core-sdk/go`.

## Public links

- Model page: https://runapi.ai/models/elevenlabs
- SDK docs: https://runapi.ai/docs#sdk-elevenlabs
- Product docs: https://runapi.ai/docs#elevenlabs
- SDK repository: https://github.com/runapi-ai/elevenlabs-sdk
- Skill repository: https://github.com/runapi-ai/elevenlabs
- Provider comparison: https://runapi.ai/providers/elevenlabs
- Full catalog: https://runapi.ai/models

## Pricing and variants

Use the most specific elevenlabs api variant page for pricing, rate limits, and commercial usage:
- [Turbo v2.5 text to speech](https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5)
- [Multilingual v2 text to speech](https://runapi.ai/models/elevenlabs/text-to-speech-multilingual-v2)
- [Dialogue v3](https://runapi.ai/models/elevenlabs/text-to-dialogue-v3)
- [Sound effects v2](https://runapi.ai/models/elevenlabs/sound-effect-v2)
- [Speech to text](https://runapi.ai/models/elevenlabs/speech-to-text)
- [Audio isolation](https://runapi.ai/models/elevenlabs/audio-isolation)

Default pricing link for the elevenlabs api SDK: https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5

## FAQ

### Which package should I install for elevenlabs api work?

Install the model package for your language: `@runapi.ai/elevenlabs`, `runapi-elevenlabs`, or `github.com/runapi-ai/elevenlabs-sdk/go`. Install core SDK packages only when you are building shared SDK infrastructure.

### Where should public links point?

Primary elevenlabs api links point to https://runapi.ai/models/elevenlabs. Pricing and usage-policy links point to variant pages such as https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5. Provider comparisons point to https://runapi.ai/providers/elevenlabs, and broad browsing points to https://runapi.ai/models.

## License

Licensed under the Apache License, Version 2.0.
