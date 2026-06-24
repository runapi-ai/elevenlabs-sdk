package ai.runapi.elevenlabs.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.elevenlabs.types.CompletedTextToDialogueResponse;
import ai.runapi.elevenlabs.types.TextToDialogueParams;
import ai.runapi.elevenlabs.types.TextToDialogueResponse;

/** Text To Dialogue operations. */
public final class TextToDialogueResource extends ElevenlabsResource {
  /** API endpoint path for text to dialogue operations. */
  public static final String ENDPOINT = "/api/v1/elevenlabs/text_to_dialogue";

  /** Creates a resource bound to the supplied transport and client options. */
  public TextToDialogueResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a text to dialogue task. */
  public TaskCreateResponse create(TextToDialogueParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a text to dialogue task with per-request options. */
  public TaskCreateResponse create(TextToDialogueParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a text to dialogue task by ID. */
  public TextToDialogueResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a text to dialogue task by ID with per-request options. */
  public TextToDialogueResponse get(String id, RequestOptions options) {
    return getTask(id, options, TextToDialogueResponse.class);
  }

  /** Creates a text to dialogue task and polls until it completes. */
  public CompletedTextToDialogueResponse run(TextToDialogueParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a text to dialogue task with per-request options and polls until it completes. */
  public CompletedTextToDialogueResponse run(TextToDialogueParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, TextToDialogueResponse.class, CompletedTextToDialogueResponse.class);
  }
}
