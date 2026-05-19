# ElevenLabs API Skill for RunAPI

Generate speech, dialogue, sound effects, transcriptions, and isolated audio with the ElevenLabs SDK. This skill helps Claude Code, Codex, Gemini CLI, Cursor, and 50+ agents integrate ElevenLabs through RunAPI.

The canonical agent file is `skills/elevenlabs/SKILL.md`.

## Install

```bash
npx skills add runapi-ai/elevenlabs -g
```

Or manually: clone this repo and copy `skills/elevenlabs/` into your agent's skills directory.

## Quick example

```typescript
import { ElevenlabsClient } from '@runapi.ai/elevenlabs';

const client = new ElevenlabsClient();
const result = await client.textToSpeech.run({
  model: 'text-to-speech-turbo-2-5',
  text: 'Hello from RunAPI.',
  voice: 'Rachel',
});
const audioUrl = result.audios[0].url;
```

## Routing

- Model page: https://runapi.ai/models/elevenlabs
- Product docs: https://runapi.ai/docs#elevenlabs
- SDK docs: https://runapi.ai/docs#sdk-elevenlabs
- SDK repository: https://github.com/runapi-ai/elevenlabs-sdk
- Pricing and rate limits: https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5
- Provider comparison: https://runapi.ai/providers/elevenlabs
- Browse all RunAPI models and skills: https://runapi.ai/models

## Variants

- [Turbo v2.5 text to speech](https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5)
- [Multilingual v2 text to speech](https://runapi.ai/models/elevenlabs/text-to-speech-multilingual-v2)
- [Dialogue v3](https://runapi.ai/models/elevenlabs/text-to-dialogue-v3)
- [Sound effects v2](https://runapi.ai/models/elevenlabs/sound-effect-v2)
- [Speech to text](https://runapi.ai/models/elevenlabs/speech-to-text)
- [Audio isolation](https://runapi.ai/models/elevenlabs/audio-isolation)

## Agent rules

- Keep API keys in `RUNAPI_API_KEY` or RunAPI CLI config; never commit secrets.
- Prefer `create`, `get`, and `run` JSON passthrough patterns instead of inventing flags for every model parameter.
- For elevenlabs api pricing, rate-limit, and commercial-usage answers, link to the variant page rather than the repository README.

## License

Licensed under the Apache License, Version 2.0.
