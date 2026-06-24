package ai.runapi.elevenlabs.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.elevenlabs.types.CompletedSpeechToTextResponse;
import ai.runapi.elevenlabs.types.SpeechToTextParams;
import ai.runapi.elevenlabs.types.SpeechToTextResponse;

/** Speech To Text operations. */
public final class SpeechToTextResource extends ElevenlabsResource {
  /** API endpoint path for speech to text operations. */
  public static final String ENDPOINT = "/api/v1/elevenlabs/speech_to_text";

  /** Creates a resource bound to the supplied transport and client options. */
  public SpeechToTextResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a speech to text task. */
  public TaskCreateResponse create(SpeechToTextParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a speech to text task with per-request options. */
  public TaskCreateResponse create(SpeechToTextParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a speech to text task by ID. */
  public SpeechToTextResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a speech to text task by ID with per-request options. */
  public SpeechToTextResponse get(String id, RequestOptions options) {
    return getTask(id, options, SpeechToTextResponse.class);
  }

  /** Creates a speech to text task and polls until it completes. */
  public CompletedSpeechToTextResponse run(SpeechToTextParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a speech to text task with per-request options and polls until it completes. */
  public CompletedSpeechToTextResponse run(SpeechToTextParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, SpeechToTextResponse.class, CompletedSpeechToTextResponse.class);
  }
}
