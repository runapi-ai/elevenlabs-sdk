package ai.runapi.elevenlabs.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.elevenlabs.types.CompletedTextToSoundResponse;
import ai.runapi.elevenlabs.types.TextToSoundParams;
import ai.runapi.elevenlabs.types.TextToSoundResponse;

/** Text To Sound operations. */
public final class TextToSoundResource extends ElevenlabsResource {
  /** API endpoint path for text to sound operations. */
  public static final String ENDPOINT = "/api/v1/elevenlabs/text_to_sound";

  /** Creates a resource bound to the supplied transport and client options. */
  public TextToSoundResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a text to sound task. */
  public TaskCreateResponse create(TextToSoundParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a text to sound task with per-request options. */
  public TaskCreateResponse create(TextToSoundParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a text to sound task by ID. */
  public TextToSoundResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a text to sound task by ID with per-request options. */
  public TextToSoundResponse get(String id, RequestOptions options) {
    return getTask(id, options, TextToSoundResponse.class);
  }

  /** Creates a text to sound task and polls until it completes. */
  public CompletedTextToSoundResponse run(TextToSoundParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a text to sound task with per-request options and polls until it completes. */
  public CompletedTextToSoundResponse run(TextToSoundParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, TextToSoundResponse.class, CompletedTextToSoundResponse.class);
  }
}
