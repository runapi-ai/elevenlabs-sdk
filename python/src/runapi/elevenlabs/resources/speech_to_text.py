"""ElevenLabs speech-to-text resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import Resource, ValidationError, RequestOptions

from ..types import CompletedSpeechToTextResponse, SpeechToTextResponse


class SpeechToText(Resource):
    """Transcribe speech audio to text with ElevenLabs models."""

    ENDPOINT = "/api/v1/elevenlabs/speech_to_text"

    RESPONSE_CLASS = SpeechToTextResponse
    COMPLETED_RESPONSE_CLASS = CompletedSpeechToTextResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create a speech-to-text task and poll until it completes.

        Args:
            **params: Speech-to-text parameters (model, prompt, ...).

        Returns:
            The completed speech-to-text response.
        """
        task = self.create(options=options, **params)
        return self._poll_until_complete(lambda: self.get(task.id, options=options))

    def create(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create a speech-to-text task and return immediately with an id.

        Args:
            **params: Speech-to-text parameters (model, prompt, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        if compacted.get("source_audio_url") is None:
            raise ValidationError("source_audio_url is required")
        return self._request("post", self.ENDPOINT, body=compacted, options=options)

    def get(self, id: str, options: Optional[RequestOptions] = None) -> Any:
        """Fetch the current status of a speech-to-text task.

        Args:
            id: Task id.

        Returns:
            The current speech-to-text status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}", options=options)
