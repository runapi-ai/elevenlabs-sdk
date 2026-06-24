package ai.runapi.elevenlabs.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.elevenlabs.types.CompletedIsolateAudioResponse;
import ai.runapi.elevenlabs.types.IsolateAudioParams;
import ai.runapi.elevenlabs.types.IsolateAudioResponse;

/** Isolate Audio operations. */
public final class IsolateAudioResource extends ElevenlabsResource {
  /** API endpoint path for isolate audio operations. */
  public static final String ENDPOINT = "/api/v1/elevenlabs/isolate_audio";

  /** Creates a resource bound to the supplied transport and client options. */
  public IsolateAudioResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a isolate audio task. */
  public TaskCreateResponse create(IsolateAudioParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a isolate audio task with per-request options. */
  public TaskCreateResponse create(IsolateAudioParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a isolate audio task by ID. */
  public IsolateAudioResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a isolate audio task by ID with per-request options. */
  public IsolateAudioResponse get(String id, RequestOptions options) {
    return getTask(id, options, IsolateAudioResponse.class);
  }

  /** Creates a isolate audio task and polls until it completes. */
  public CompletedIsolateAudioResponse run(IsolateAudioParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a isolate audio task with per-request options and polls until it completes. */
  public CompletedIsolateAudioResponse run(IsolateAudioParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, IsolateAudioResponse.class, CompletedIsolateAudioResponse.class);
  }
}
