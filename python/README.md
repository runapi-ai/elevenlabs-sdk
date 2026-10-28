# ElevenLabs Python SDK for RunAPI

The ElevenLabs Python SDK is the language-specific package for ElevenLabs on RunAPI. Use this package for text-to-speech and dialogue generation workflows when your application needs request bodies, task status lookup, and consistent RunAPI errors in Python.

This README is the Python package guide inside the public `elevenlabs-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/elevenlabs; for API reference, use https://runapi.ai/docs/api/elevenlabs/text-to-speech; for SDK docs, use https://runapi.ai/docs/resources/sdks.

## Retired sound effects and transcription

`sound-effect-v2` and `speech-to-text` were retired on 2026-10-14. Valid new requests return HTTP 410 before task creation or balance deduction. Neither old ID maps to a replacement. Historical GET remains available for existing tasks through `client.text_to_sound.get(task_id)` and `client.speech_to_text.get(task_id)`.

## Audio Isolation retirement

[Audio Isolation](https://runapi.ai/models/elevenlabs/audio-isolation) was retired on 2026-10-09. New `isolate_audio` requests return HTTP 410 before a task is created or any balance is deducted. The model name `audio-isolation` does not map to another model. Existing isolation task IDs remain queryable through the SDK task-status lookup.

## Install

```bash
pip install runapi-elevenlabs
```

## Quick start

```python
from runapi.elevenlabs import ElevenlabsClient

client = ElevenlabsClient()  # reads RUNAPI_API_KEY, or pass api_key="sk-..."

task = client.text_to_speech.create(
    model="text-to-speech-turbo-v2.5",
    text="Hello from RunAPI",
)
status = client.text_to_speech.get(task.id)
```

Use `create` when you want to submit a task and return quickly, `get` when you need the latest task state, and `run` when a script should create and poll until completion:

```python
result = client.text_to_speech.run(
    model="text-to-speech-turbo-v2.5",
    text="A calm narrator voice reading the morning news",
)
print(result.audios[0].url)
```

In web request handlers, prefer `create` plus webhook or later `get` polling so a worker is not held open.

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## Language notes

Pass parameters as keyword arguments and catch the `runapi.elevenlabs` error classes when building audio jobs or scripts. Use `text_to_speech` and `text_to_dialogue` for new tasks; `text_to_sound` and `speech_to_text` retain historical task lookup only. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys or callback secrets.

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
