"""ElevenLabs text-to-speech resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import Resource, RequestOptions

from ..types import (
    AudioTaskResponse,
    CompletedAudioTaskResponse,
)


class TextToSpeech(Resource):
    """Generate speech audio from text with ElevenLabs models."""

    ENDPOINT = "/api/v1/elevenlabs/text_to_speech"

    RESPONSE_CLASS = AudioTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedAudioTaskResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create a text-to-speech task and poll until it completes.

        Args:
            **params: Text-to-speech parameters (model, prompt, ...).

        Returns:
            The completed text-to-speech response.
        """
        task = self.create(options=options, **params)
        return self._poll_until_complete(lambda: self.get(task.id, options=options))

    def create(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create a text-to-speech task and return immediately with an id.

        Args:
            **params: Text-to-speech parameters (model, prompt, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        return self._request("post", self.ENDPOINT, body=compacted, options=options)

    def get(self, id: str, options: Optional[RequestOptions] = None) -> Any:
        """Fetch the current status of a text-to-speech task.

        Args:
            id: Task id.

        Returns:
            The current text-to-speech status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}", options=options)
