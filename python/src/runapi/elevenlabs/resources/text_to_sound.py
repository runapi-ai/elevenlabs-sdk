"""ElevenLabs text-to-sound resource."""

from __future__ import annotations

from typing import Any

from runapi.core import Resource, ValidationError

from ..types import (
    TEXT_TO_SOUND_OUTPUT_FORMATS,
    AudioTaskResponse,
    CompletedAudioTaskResponse,
)


class TextToSound(Resource):
    """Generate sound effects from text prompts with ElevenLabs models."""

    ENDPOINT = "/api/v1/elevenlabs/text_to_sound"

    RESPONSE_CLASS = AudioTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedAudioTaskResponse

    def run(self, **params: Any) -> Any:
        """Create a text-to-sound task and poll until it completes.

        Args:
            **params: Text-to-sound parameters (model, prompt, ...).

        Returns:
            The completed text-to-sound response.
        """
        task = self.create(**params)
        return self._poll_until_complete(lambda: self.get(task.id))

    def create(self, **params: Any) -> Any:
        """Create a text-to-sound task and return immediately with an id.

        Args:
            **params: Text-to-sound parameters (model, prompt, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        if compacted.get("text") is None:
            raise ValidationError("text is required")
        output_format = compacted.get("output_format")
        if output_format is not None and output_format not in TEXT_TO_SOUND_OUTPUT_FORMATS:
            raise ValidationError("Invalid output_format")
        return self._request("post", self.ENDPOINT, body=compacted)

    def get(self, id: str) -> Any:
        """Fetch the current status of a text-to-sound task.

        Args:
            id: Task id.

        Returns:
            The current text-to-sound status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}")
