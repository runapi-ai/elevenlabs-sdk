"""ElevenLabs text-to-speech resource."""

from __future__ import annotations

from typing import Any, Dict

from runapi.core import Resource, ValidationError

from ..contract_gen import CONTRACT
from ..types import (
    AudioTaskResponse,
    CompletedAudioTaskResponse,
)


class TextToSpeech(Resource):
    """Generate speech audio from text with ElevenLabs models."""

    ENDPOINT = "/api/v1/elevenlabs/text_to_speech"

    RESPONSE_CLASS = AudioTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedAudioTaskResponse

    def run(self, **params: Any) -> Any:
        """Create a text-to-speech task and poll until it completes.

        Args:
            **params: Text-to-speech parameters (model, prompt, ...).

        Returns:
            The completed text-to-speech response.
        """
        task = self.create(**params)
        return self._poll_until_complete(lambda: self.get(task.id))

    def create(self, **params: Any) -> Any:
        """Create a text-to-speech task and return immediately with an id.

        Args:
            **params: Text-to-speech parameters (model, prompt, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        self._validate_params(compacted)
        return self._request("post", self.ENDPOINT, body=compacted)

    def get(self, id: str) -> Any:
        """Fetch the current status of a text-to-speech task.

        Args:
            id: Task id.

        Returns:
            The current text-to-speech status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}")

    def _validate_params(self, params: Dict[str, Any]) -> None:
        self._validate_contract(CONTRACT["text-to-speech"], params)
        if params.get("model") == "text-to-speech-multilingual-v2" and params.get("voice") is None:
            raise ValidationError("voice is required for text-to-speech-multilingual-v2")
