package ai.runapi.elevenlabs;

import ai.runapi.core.BaseClient;
import ai.runapi.core.ClientOptions;
import ai.runapi.core.http.HttpTransport;
import java.net.URI;
import ai.runapi.elevenlabs.resources.IsolateAudioResource;
import ai.runapi.elevenlabs.resources.SpeechToTextResource;
import ai.runapi.elevenlabs.resources.TextToDialogueResource;
import ai.runapi.elevenlabs.resources.TextToSoundResource;
import ai.runapi.elevenlabs.resources.TextToSpeechResource;

/** ElevenLabs model-family Java SDK client. */
public final class ElevenLabsClient extends BaseClient {
  private final IsolateAudioResource isolateAudio;
  private final SpeechToTextResource speechToText;
  private final TextToDialogueResource textToDialogue;
  private final TextToSoundResource textToSound;
  private final TextToSpeechResource textToSpeech;

  private ElevenLabsClient(ClientOptions options) {
    super(options);
    this.isolateAudio = new IsolateAudioResource(transport(), options());
    this.speechToText = new SpeechToTextResource(transport(), options());
    this.textToDialogue = new TextToDialogueResource(transport(), options());
    this.textToSound = new TextToSoundResource(transport(), options());
    this.textToSpeech = new TextToSpeechResource(transport(), options());
  }

  /** Creates a new ElevenLabsClient builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Isolate Audio operations. */
  public IsolateAudioResource isolateAudio() {
    return isolateAudio;
  }

  /** Speech To Text operations. */
  public SpeechToTextResource speechToText() {
    return speechToText;
  }

  /** Text To Dialogue operations. */
  public TextToDialogueResource textToDialogue() {
    return textToDialogue;
  }

  /** Text To Sound operations. */
  public TextToSoundResource textToSound() {
    return textToSound;
  }

  /** Text To Speech operations. */
  public TextToSpeechResource textToSpeech() {
    return textToSpeech;
  }

  /** Builder for {@link ElevenLabsClient}. */
  public static final class Builder extends BaseClient.Builder<Builder> {
    private Builder() {}

    /** Sets the API key. If omitted, the SDK reads {@code RUNAPI_API_KEY}. */
    @Override
    public Builder apiKey(String value) {
      return super.apiKey(value);
    }

    /** Sets the RunAPI base URL. If omitted, the SDK reads {@code RUNAPI_BASE_URL}. */
    @Override
    public Builder baseUrl(String value) {
      return super.baseUrl(value);
    }

    /** Sets the RunAPI base URL from a URI. */
    @Override
    public Builder baseUrl(URI value) {
      return super.baseUrl(value);
    }

    /** Sets a custom HTTP transport. User-provided transports are not closed by SDK clients. */
    @Override
    public Builder transport(HttpTransport value) {
      return super.transport(value);
    }

    /** Builds an immutable ElevenLabsClient. */
    @Override
    public ElevenLabsClient build() {
      return new ElevenLabsClient(options.build());
    }
  }
}
