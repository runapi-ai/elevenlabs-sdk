import pytest

from runapi.core import config
from runapi.core.errors import AuthenticationError, ValidationError
from runapi.elevenlabs import ElevenlabsClient
from runapi.elevenlabs.resources.isolate_audio import IsolateAudio
from runapi.elevenlabs.resources.speech_to_text import SpeechToText
from runapi.elevenlabs.resources.text_to_dialogue import TextToDialogue
from runapi.elevenlabs.resources.text_to_sound import TextToSound
from runapi.elevenlabs.resources.text_to_speech import TextToSpeech
from runapi.elevenlabs.types import (
    AudioTaskResponse,
    CompletedAudioTaskResponse,
    CompletedSpeechToTextResponse,
    SpeechToTextResponse,
)


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        if self._responses:
            return self._responses.pop(0)
        return {"id": "task_1", "status": "pending"}


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)
    yield


# --- auth -----------------------------------------------------------------


def test_accepts_api_key_parameter():
    assert isinstance(ElevenlabsClient(api_key="k", http_client=FakeHttp()), ElevenlabsClient)


def test_falls_back_to_global(monkeypatch):
    monkeypatch.setattr(config, "api_key", "global-key")
    assert isinstance(ElevenlabsClient(http_client=FakeHttp()), ElevenlabsClient)


def test_falls_back_to_env(monkeypatch):
    monkeypatch.setenv("RUNAPI_API_KEY", "env-key")
    assert isinstance(ElevenlabsClient(http_client=FakeHttp()), ElevenlabsClient)


def test_raises_without_api_key():
    with pytest.raises(AuthenticationError, match="API key is required"):
        ElevenlabsClient()


# --- injection / accessors ------------------------------------------------


def test_uses_injected_http_client():
    fake = FakeHttp()
    client = ElevenlabsClient(api_key="k", http_client=fake)
    assert client.text_to_speech._http is fake
    assert client.text_to_dialogue._http is fake
    assert client.text_to_sound._http is fake
    assert client.speech_to_text._http is fake
    assert client.isolate_audio._http is fake


def test_exposes_resource_accessors():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    assert isinstance(client.text_to_speech, TextToSpeech)
    assert isinstance(client.text_to_dialogue, TextToDialogue)
    assert isinstance(client.text_to_sound, TextToSound)
    assert isinstance(client.speech_to_text, SpeechToText)
    assert isinstance(client.isolate_audio, IsolateAudio)


# --- request shapes -------------------------------------------------------


def test_text_to_speech_create_posts_compacted_body():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = ElevenlabsClient(api_key="k", http_client=fake)
    result = client.text_to_speech.create(
        model="text-to-speech-turbo-v2.5", text="hello", voice=None
    )
    assert fake.calls == [
        ("post", "/api/v1/elevenlabs/text_to_speech", {"model": "text-to-speech-turbo-v2.5", "text": "hello"}),
    ]
    assert isinstance(result, AudioTaskResponse)


def test_text_to_speech_get_fetches_by_id():
    fake = FakeHttp({"id": "t1", "status": "processing"})
    client = ElevenlabsClient(api_key="k", http_client=fake)
    client.text_to_speech.get("t1")
    assert fake.calls == [("get", "/api/v1/elevenlabs/text_to_speech/t1", None)]


def test_text_to_dialogue_create_shape():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = ElevenlabsClient(api_key="k", http_client=fake)
    client.text_to_dialogue.create(dialogue=[{"text": "Hi", "voice": "Rachel"}])
    assert fake.calls == [
        ("post", "/api/v1/elevenlabs/text_to_dialogue", {"dialogue": [{"text": "Hi", "voice": "Rachel"}]}),
    ]


def test_text_to_sound_create_shape():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = ElevenlabsClient(api_key="k", http_client=fake)
    client.text_to_sound.create(text="rain on a tin roof", output_format="mp3_44100_128")
    assert fake.calls == [
        ("post", "/api/v1/elevenlabs/text_to_sound", {"text": "rain on a tin roof", "output_format": "mp3_44100_128"}),
    ]


def test_speech_to_text_create_shape():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = ElevenlabsClient(api_key="k", http_client=fake)
    result = client.speech_to_text.create(source_audio_url="https://x/a.mp3")
    assert fake.calls == [
        ("post", "/api/v1/elevenlabs/speech_to_text", {"source_audio_url": "https://x/a.mp3"}),
    ]
    assert isinstance(result, SpeechToTextResponse)


def test_isolate_audio_create_shape():
    fake = FakeHttp({"id": "t1", "status": "pending"})
    client = ElevenlabsClient(api_key="k", http_client=fake)
    client.isolate_audio.create(source_audio_url="https://x/a.mp3")
    assert fake.calls == [
        ("post", "/api/v1/elevenlabs/isolate_audio", {"source_audio_url": "https://x/a.mp3"}),
    ]


# --- run() narrowing ------------------------------------------------------


def test_text_to_speech_run_narrows_completed_type():
    fake = FakeHttp(
        {"id": "t1", "status": "pending"},
        {"id": "t1", "status": "completed", "audios": [{"url": "https://x/y.mp3"}]},
    )
    client = ElevenlabsClient(api_key="k", http_client=fake)
    result = client.text_to_speech.run(model="text-to-speech-turbo-v2.5", text="hi there")
    assert isinstance(result, CompletedAudioTaskResponse)
    assert result.audios[0].url == "https://x/y.mp3"


def test_speech_to_text_run_narrows_completed_type():
    fake = FakeHttp(
        {"id": "t1", "status": "pending"},
        {"id": "t1", "status": "completed", "text": "transcribed words"},
    )
    client = ElevenlabsClient(api_key="k", http_client=fake)
    result = client.speech_to_text.run(source_audio_url="https://x/a.mp3")
    assert isinstance(result, CompletedSpeechToTextResponse)
    assert result.text == "transcribed words"


# --- validation -----------------------------------------------------------


def test_text_to_speech_requires_model():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="model is required"):
        client.text_to_speech.create(text="hi")


def test_text_to_speech_rejects_unknown_model():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="Invalid model: nope"):
        client.text_to_speech.create(model="nope", text="hi")


def test_text_to_speech_requires_text():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="text is required"):
        client.text_to_speech.create(model="text-to-speech-turbo-v2.5")


def test_text_to_speech_multilingual_requires_voice():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="voice is required for text-to-speech-multilingual-v2"):
        client.text_to_speech.create(model="text-to-speech-multilingual-v2", text="hi")


def test_text_to_dialogue_requires_dialogue():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="dialogue is required"):
        client.text_to_dialogue.create()


def test_text_to_sound_requires_text():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="text is required"):
        client.text_to_sound.create()


def test_text_to_sound_rejects_invalid_output_format():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="Invalid output_format"):
        client.text_to_sound.create(text="rain", output_format="flac_99")


def test_speech_to_text_requires_source_audio_url():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="source_audio_url is required"):
        client.speech_to_text.create()


def test_isolate_audio_requires_source_audio_url():
    client = ElevenlabsClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="source_audio_url is required"):
        client.isolate_audio.create()
