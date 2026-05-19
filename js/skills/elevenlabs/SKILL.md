---
name: elevenlabs
description: Generate and process audio (text-to-speech, multi-voice text-to-dialogue, text-to-sound, speech-to-text, isolate audio) through RunAPI.ai using the @runapi.ai/elevenlabs Node/TypeScript SDK. Use when the user asks to add text-to-speech, voice synthesis, speech-to-text, or text-to-sound, or writes against @runapi.ai/elevenlabs. Triggers on "elevenlabs", "text to speech", "TTS", "语音合成", "speech-to-text", "转录", "text-to-sound", "@runapi.ai/elevenlabs".
documentation: https://runapi.ai/models/elevenlabs
provider_page: https://runapi.ai/providers/elevenlabs
catalog: https://runapi.ai/models
---

# @runapi.ai/elevenlabs — RunAPI.ai Elevenlabs audio generation

Build Node / TypeScript integrations that generate speech, text-to-dialogue, text-to-sound, speech-to-text, and isolated audio through RunAPI.ai.

## Setup

Requires **Node 18+** (global `fetch`).

```bash
npm install @runapi.ai/elevenlabs
```

Set your API key in the environment:

```dotenv
# .env
RUNAPI_API_KEY=runapi_xxx   # get one at https://runapi.ai/settings/api_keys
```

```ts
import { ElevenlabsClient } from '@runapi.ai/elevenlabs';

// The SDK reads RUNAPI_API_KEY from the environment automatically.
const client = new ElevenlabsClient();
```

Pass `{ apiKey }` explicitly if you manage secrets differently. `baseUrl` defaults to `https://runapi.ai`; override only for local development.

## Core recipe — text to speech

```ts
const result = await client.textToSpeech.run({
  model: 'text-to-speech-turbo-2-5',
  text: 'Hello from RunAPI.',
  voice: 'Rachel',
});

const audioUrl = result.audios[0].url;
```

`run()` creates the task, auto-polls, and resolves only when the task completes — `audios[0].url` is guaranteed on the resolved value (for speech-to-text, `text` is guaranteed). On failure it throws `TaskFailedError`; on polling timeout it throws `TaskTimeoutError`. Use `run()` for scripts and short-lived processes. For request handlers, split it:

```ts
const { id } = await client.textToSpeech.create({ model: 'text-to-speech-turbo-2-5', text: '...', voice: 'Rachel' });
// return 202 immediately; fetch later:
const status = await client.textToSpeech.get(id);
if (status.status === 'completed') { /* ... */ }
```

Do not hold a web worker open waiting on `run()`. Split + webhook is the production pattern.

`run()` polls every 2 s for up to 15 min by default. Tune when needed:

```ts
await client.textToSpeech.run(params, { maxWaitMs: 2 * 60_000, pollIntervalMs: 1_000 });
```

If `TaskTimeoutError` fires, the task is still running server-side — resume with `<resource>.get(id)` or finish via webhook.

## Multi-voice dialogue

Sequence lines with per-line `voice`:

```ts
const dialogue = await client.textToDialogue.run({
  dialogue: [
    { voice: 'Rachel', text: 'So what did you think?' },
    { voice: 'Adam',   text: 'Honestly, it was incredible.' },
  ],
  stability: 0.5,
  language_code: 'en',
});

console.log(dialogue.audios[0].url);
```

## Sound effects

```ts
const fx = await client.textToSound.run({
  text: 'Thunderclap followed by rain on a tin roof',
  duration_seconds: 6,
  loop: false,
  output_format: 'mp3_44100_128',
});
```

## Speech to Text (audio in → text out)

```ts
const t = await client.speechToText.run({
  audio_url: 'https://cdn.example.com/meeting.mp3',
  diarize: true,
  tag_audio_events: true,
  language_code: 'en',
});

console.log(t.text);
```

## Audio isolation (remove background noise)

```ts
const isolated = await client.isolateAudio.run({
  audio_url: 'https://cdn.example.com/noisy.mp3',
});

console.log(isolated.audios[0].url);
```

## Models

| Resource | `model` values |
|---|---|
| `textToSpeech` | `text-to-speech-turbo-2-5`, `text-to-speech-multilingual-v2` |
| `textToDialogue` | — (no `model` field; server picks the engine) |
| `textToSound` | — |
| `speechToText` | — |
| `isolateAudio` | — |

Pick the turbo model for the lowest latency; pick multilingual v2 for non-English voices. `voice` accepts Elevenlabs voice IDs or named voices (e.g. `'Rachel'`, `'Adam'`).

Exact credit costs are shown at https://runapi.ai/pricing and in the dashboard — do not hardcode prices in application code.

## Callbacks (webhooks)

Pass `callback_url` on `create()` (or any `run()` call) and RunAPI will POST the final payload to you:

```ts
await client.textToSpeech.create({
  model: 'text-to-speech-turbo-2-5',
  text: '...',
  voice: 'Rachel',
  callback_url: 'https://your.app/webhooks/runapi/elevenlabs',
});
```

Payload shape (audio resources):

```ts
{ id: string; status: 'completed' | 'failed'; audios?: { url: string }[]; error?: string }
```

SpeechToText return `text: string` instead of `audios`.

**Always verify the signature before trusting the body.** RunAPI signs every callback with your account's Callback Secret (rotate at `/accounts/callback_secret`). Headers:

- `X-Callback-Id` — UUID, store to make handler idempotent
- `X-Callback-Timestamp` — unix seconds, reject if `|now - ts| > 300`
- `X-Callback-Signature` — base64 HMAC-SHA256 over `` `${id}.${ts}.${rawBody}` `` using the base64-decoded secret

```ts
import crypto from 'node:crypto';

function verify(raw: string, id: string, ts: string, sig: string, secret: string) {
  const key = Buffer.from(secret, 'base64');
  const mac = crypto.createHmac('sha256', key)
    .update(`${id}.${ts}.${raw}`)
    .digest('base64');
  return crypto.timingSafeEqual(Buffer.from(mac), Buffer.from(sig));
}
```

Reply `2xx` within 10s; any non-2xx triggers retries.

## Errors

All errors are re-exported from `@runapi.ai/core`. Always `instanceof` — never string-match messages.

| Error | Status | Action |
|---|---|---|
| `AuthenticationError` | 401 | abort; surface "reconnect your API key" |
| `InsufficientCreditsError` | 402 | prompt user to top up at runapi.ai/billing |
| `ValidationError` | 400 / 422 | fix params; do not retry |
| `RateLimitError` | 429 | sleep `err.retryAfterMs`, then retry |
| `ServiceUnavailableError` | 503 / 455 | retry with backoff; transient service issue |
| `TaskFailedError` | — | show `err.details` to user; do not auto-retry |
| `TaskTimeoutError` | — | re-poll with `<resource>.get(id)` |

```ts
import { InsufficientCreditsError, TaskFailedError } from '@runapi.ai/elevenlabs';

try {
  await client.textToSpeech.run({ model: 'text-to-speech-turbo-2-5', text: '...', voice: 'Rachel' });
} catch (err) {
  if (err instanceof InsufficientCreditsError) { /* surface top-up CTA */ }
  else if (err instanceof TaskFailedError)       { /* show err.details */ }
  else throw err;
}
```

## Gotchas

- `textToSpeech` requires `model`; `textToDialogue`, `textToSound`, `speechToText`, and `isolateAudio` do not take a `model` field.
- `textToDialogue` is an array of `{ voice, text }` lines — voice is per-line, not per-request.
- `text-to-dialogue.stability` is a fixed enum: `0`, `0.5`, or `1`.
- `speechToText.run()` returns `{ text }`, not `{ audios }` — the response shape is different from the other resources.
- `output_format` on `textToSound` covers a wide codec/bitrate matrix (`mp3_*`, `pcm_*`, `ulaw_8000`, `alaw_8000`, `opus_*`) — pick one from the type union, do not invent strings.
- `callback_url` must be reachable from the public internet. `localhost` / `127.0.0.1` URLs will never fire — use a tunnel (cloudflared, ngrok, tailscale funnel) when developing locally.

## Dig deeper

Package README (full API surface, all params): `node_modules/@runapi.ai/elevenlabs/README.md`. Types: `@runapi.ai/elevenlabs/dist/types.d.ts`. Product docs: https://runapi.ai/docs.
