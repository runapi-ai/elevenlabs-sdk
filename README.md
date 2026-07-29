<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/elevenlabs-sdk">ElevenLabs API SDK for RunAPI</a>
</h3>

<p align="center">
  ElevenLabs API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/elevenlabs)](https://www.npmjs.com/package/@runapi.ai/elevenlabs)
[![PyPI](https://img.shields.io/pypi/v/runapi-elevenlabs)](https://pypi.org/project/runapi-elevenlabs/)
[![RubyGems](https://img.shields.io/gem/v/runapi-elevenlabs)](https://rubygems.org/gems/runapi-elevenlabs)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/elevenlabs-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/elevenlabs-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-elevenlabs)](https://central.sonatype.com/artifact/ai.runapi/runapi-elevenlabs)
[![License](https://img.shields.io/github/license/runapi-ai/elevenlabs-sdk)](https://github.com/runapi-ai/elevenlabs-sdk/blob/main/LICENSE)

</div>
<br/>

The ElevenLabs API SDK packages JavaScript, Python, Ruby, Go, Java, and PHP clients for ElevenLabs on RunAPI. Use it for text-to-speech, dialogue generation, sound effects, speech transcription, and audio isolation workflows when your app needs typed request builders, predictable task polling, file upload helpers, account helpers, and consistent RunAPI errors.

ElevenLabs is listed in the RunAPI model catalog at https://runapi.ai/models/elevenlabs. Variant pages below carry pricing, rate-limit, and commercial-usage details. The public `elevenlabs-sdk` repository groups the non-PHP language packages, examples, CI, and release tags for this model. The PHP package is released from a split Composer repository.

## Install

```bash
npm install @runapi.ai/elevenlabs
pip install runapi-elevenlabs
gem install runapi-elevenlabs
go get github.com/runapi-ai/elevenlabs-sdk/go@latest
```

Gradle:

```kotlin
dependencies {
  implementation("ai.runapi:runapi-elevenlabs:0.1.1")
}
```

Maven:

```xml
<dependency>
  <groupId>ai.runapi</groupId>
  <artifactId>runapi-elevenlabs</artifactId>
  <version>0.1.1</version>
</dependency>
```

Use the Java BOM when installing multiple RunAPI Java modules:

```kotlin
dependencies {
  implementation(platform("ai.runapi:runapi-bom:0.2.8"))
  implementation("ai.runapi:runapi-elevenlabs")
}
```

The PHP package is published from the split Composer repository as `runapi-ai/elevenlabs`; see https://github.com/runapi-ai/elevenlabs-php for PHP install and examples.

## What you can build

- Build apps, agent workflows, batch jobs, and production services around ElevenLabs requests.
- Install only the language package your app needs while keeping one model-specific repository for docs and releases.
- Use `create` for submit-only jobs, `get` for status lookup, and `run` for submit-and-poll scripts.
- Upload local files, URL files, or base64 files through shared RunAPI file helpers.
- Handle validation, authentication, rate limits, insufficient credits, task failures, and polling timeouts through RunAPI SDK errors.

## Java quick start

```java
import ai.runapi.elevenlabs.ElevenLabsClient;
import ai.runapi.elevenlabs.types.TextToSpeechParams;
import ai.runapi.elevenlabs.types.CompletedTextToSpeechResponse;
import ai.runapi.elevenlabs.types.TextToSpeechModel;

ElevenLabsClient client = ElevenLabsClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

CompletedTextToSpeechResponse result = client.textToSpeech().run(
    TextToSpeechParams.builder()
        .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
        .text("Narrate this sentence in a warm studio voice")
        .build()
);
```

Java packages target Java 8 bytecode and are tested on Java 8, 11, 17, and 21. Each model artifact depends on `ai.runapi:runapi-core`, so application code normally installs only `ai.runapi:runapi-elevenlabs`.

## Task lifecycle

Most media endpoints are asynchronous. `create()` submits a task and returns its id, `get(id)` fetches the latest task state, and `run(params)` creates the task and polls until it reaches a terminal state. In web request handlers, prefer `create()` plus webhook or later `get()` polling so the server does not hold a worker open.

## Repository layout

- `js/` publishes `@runapi.ai/elevenlabs`.
- `python/` publishes `runapi-elevenlabs`.
- `ruby/` publishes `runapi-elevenlabs`.
- `go/` publishes `github.com/runapi-ai/elevenlabs-sdk/go`.
- `java/` publishes `ai.runapi:runapi-elevenlabs` and uses `ai.runapi:runapi-core`.

## Public links

- Model page: https://runapi.ai/models/elevenlabs
- SDK docs: https://runapi.ai/docs/resources/sdks
- Product docs: https://runapi.ai/docs/api/elevenlabs/text-to-speech
- SDK repository: https://github.com/runapi-ai/elevenlabs-sdk
- PHP package repository: https://github.com/runapi-ai/elevenlabs-php
- Skill repository: https://github.com/runapi-ai/elevenlabs
- Provider comparison: https://runapi.ai/providers/elevenlabs
- Full catalog: https://runapi.ai/models

## Pricing and variants

Use the most specific ElevenLabs variant page for pricing, rate limits, and commercial usage:
- [Turbo v2.5 text to speech](https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5)
- [Multilingual v2 text to speech](https://runapi.ai/models/elevenlabs/text-to-speech-multilingual-v2)
- [Dialogue v3](https://runapi.ai/models/elevenlabs/text-to-dialogue-v3)
- [Sound effects v2](https://runapi.ai/models/elevenlabs/sound-effect-v2)
- [Speech to text](https://runapi.ai/models/elevenlabs/speech-to-text)
- [Audio isolation](https://runapi.ai/models/elevenlabs/audio-isolation)

Default pricing link for the ElevenLabs SDK: https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5

## File storage

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## FAQ

### Which package should I install for ElevenLabs work?

Install the model package for your language: `@runapi.ai/elevenlabs` on npm, `runapi-elevenlabs` on PyPI, `runapi-elevenlabs` on RubyGems, `github.com/runapi-ai/elevenlabs-sdk/go`, `ai.runapi:runapi-elevenlabs` on Maven Central, or `runapi-ai/elevenlabs` on Packagist. Install core SDK packages only when you are building shared SDK infrastructure.

### Where should public links point?

Primary ElevenLabs links point to https://runapi.ai/models/elevenlabs. Pricing and usage-policy links point to variant pages such as https://runapi.ai/models/elevenlabs/text-to-speech-turbo-v2.5. Provider comparisons point to https://runapi.ai/providers/elevenlabs, and broad browsing points to https://runapi.ai/models.

## License

Licensed under the Apache License, Version 2.0.
