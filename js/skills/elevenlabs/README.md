<p align="center">
  <a href="https://github.com/runapi-ai/elevenlabs">
    <h3 align="center">ElevenLabs API Skill for RunAPI</h3>
  </a>
</p>

<p align="center">
  Install this agent skill, inspect ElevenLabs fields, then run jobs through the RunAPI CLI.
</p>

<p align="center">
  <a href="https://runapi.ai/models/elevenlabs"><strong>Model Reference</strong></a> · <a href="https://github.com/runapi-ai/cli"><strong>CLI</strong></a> · <a href="https://github.com/runapi-ai/elevenlabs-sdk"><strong>SDK</strong></a>
</p>

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/elevenlabs)](https://www.skills.sh/runapi-ai/elevenlabs/elevenlabs)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--elevenlabs-111827)](https://clawhub.ai/runapi-ai/runapi-elevenlabs)
[![License](https://img.shields.io/github/license/runapi-ai/elevenlabs)](https://github.com/runapi-ai/elevenlabs/blob/main/LICENSE)

</div>
<br/>

Generate speech, dialogue, sound effects, transcriptions, and isolated audio with the ElevenLabs SDK. This skill helps Claude Code, Codex, Gemini CLI, Cursor, and 50+ agents integrate ElevenLabs through RunAPI.

The canonical agent file is `skills/elevenlabs/SKILL.md`.

## Install

```bash
npx skills add runapi-ai/elevenlabs -g
```

Or paste this prompt to your AI agent:

```text
Install the elevenlabs skill for me:

1. Clone https://github.com/runapi-ai/elevenlabs
2. Copy the skills/elevenlabs/ directory into your
   user-level skills directory (e.g. ~/.claude/skills/
   for Claude Code, ~/.codex/skills/ for Codex).
3. Verify that SKILL.md is present.
4. Confirm the install path when done.
```

## Quick example

```typescript
import { ElevenlabsClient } from '@runapi.ai/elevenlabs';

const client = new ElevenlabsClient();
const result = await client.textToSpeech.run({
  model: 'text-to-speech-turbo-v2.5',
  text: 'Hello from RunAPI.',
  voice: 'EkK5I93UQWFDigLMpZcX',
});
const audioUrl = result.audios[0].url;
```

## Routing

- Model page: https://runapi.ai/models/elevenlabs
- Product docs: https://runapi.ai/docs/api/elevenlabs/text-to-speech
- SDK docs: https://runapi.ai/docs/resources/sdks
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

- Integration work uses the target language SDK; one-off generation, manual smoke tests, debugging, or user-requested CLI runs use the RunAPI CLI skill: https://github.com/runapi-ai/cli-skill
- RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.
- Keep API keys in `RUNAPI_API_KEY` or RunAPI CLI config; never commit secrets.
- Prefer `create`, `get`, and `run` JSON passthrough patterns instead of inventing flags for every model parameter.
- For pricing, rate-limit, and commercial-usage answers, link to the variant page rather than the repository README.

## License

Licensed under the Apache License, Version 2.0.
