"""ElevenLabs client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ClientOptions, HttpClient, resolve_api_key

from .resources.isolate_audio import IsolateAudio
from .resources.speech_to_text import SpeechToText
from .resources.text_to_dialogue import TextToDialogue
from .resources.text_to_sound import TextToSound
from .resources.text_to_speech import TextToSpeech


class ElevenlabsClient:
    """ElevenLabs speech, dialogue, sound, and audio client.

    Example::

        client = ElevenlabsClient(api_key="sk-...")
        result = client.text_to_speech.run(
            model="text-to-speech-turbo-v2.5", text="Hello from RunAPI"
        )
    """

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        resolved_api_key = resolve_api_key(api_key)
        client_options = ClientOptions(api_key=resolved_api_key, **options)
        http = client_options.http_client or HttpClient(client_options)
        self.text_to_speech = TextToSpeech(http)
        self.text_to_dialogue = TextToDialogue(http)
        self.text_to_sound = TextToSound(http)
        self.speech_to_text = SpeechToText(http)
        self.isolate_audio = IsolateAudio(http)
