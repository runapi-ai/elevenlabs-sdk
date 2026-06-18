"""ElevenLabs model lists, enums, and response models."""

from __future__ import annotations

from runapi.core import BaseModel, TaskResponse, optional, required

TEXT_TO_SPEECH_MODELS = [
    "text-to-speech-turbo-v2.5",
    "text-to-speech-multilingual-v2",
]
DEFAULT_TEXT_TO_SPEECH_VOICE = "EkK5I93UQWFDigLMpZcX"
TEXT_TO_SOUND_OUTPUT_FORMATS = [
    "mp3_22050_32",
    "mp3_44100_32",
    "mp3_44100_64",
    "mp3_44100_96",
    "mp3_44100_128",
    "mp3_44100_192",
    "pcm_8000",
    "pcm_16000",
    "pcm_22050",
    "pcm_24000",
    "pcm_44100",
    "pcm_48000",
    "ulaw_8000",
    "alaw_8000",
    "opus_48000_32",
    "opus_48000_64",
    "opus_48000_96",
    "opus_48000_128",
    "opus_48000_192",
]


class Audio(BaseModel):
    url = optional(str)


class AsyncTaskResponse(TaskResponse):
    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)


class AudioTaskResponse(AsyncTaskResponse):
    """Task status/result for ElevenLabs audio generation."""
    audios = optional([lambda: Audio])
    error = optional(str)


class SpeechToTextResponse(AsyncTaskResponse):
    """Task status/result for ElevenLabs speech-to-text."""
    text = optional(str)
    error = optional(str)


# Narrowed responses returned by ``run()`` methods once polling observes
# ``status: "completed"``. Result fields are required so consumers never have to
# null-check them on a successful task.
class CompletedAudioTaskResponse(AudioTaskResponse):
    audios = required([lambda: Audio])


class CompletedSpeechToTextResponse(SpeechToTextResponse):
    text = required(str)
