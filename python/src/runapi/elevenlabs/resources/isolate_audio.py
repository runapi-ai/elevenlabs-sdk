"""ElevenLabs isolate-audio resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import Resource, RequestOptions

from ..types import AudioTaskResponse, CompletedAudioTaskResponse


class IsolateAudio(Resource):
    """Isolate voice from background noise in audio with ElevenLabs models."""

    ENDPOINT = "/api/v1/elevenlabs/isolate_audio"

    RESPONSE_CLASS = AudioTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedAudioTaskResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create an isolate-audio task and poll until it completes.

        Args:
            **params: Isolate-audio parameters (model, prompt, ...).

        Returns:
            The completed isolate-audio response.
        """
        task = self.create(options=options, **params)
        return self._poll_until_complete(lambda: self.get(task.id, options=options))

    def create(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Create an isolate-audio task and return immediately with an id.

        Args:
            **params: Isolate-audio parameters (model, prompt, ...).

        Returns:
            The task creation result with an id.
        """
        compacted = self._compact_params(params)
        return self._request("post", self.ENDPOINT, body=compacted, options=options)

    def get(self, id: str, options: Optional[RequestOptions] = None) -> Any:
        """Fetch the current status of an isolate-audio task.

        Args:
            id: Task id.

        Returns:
            The current isolate-audio status.
        """
        return self._request("get", f"{self.ENDPOINT}/{id}", options=options)
