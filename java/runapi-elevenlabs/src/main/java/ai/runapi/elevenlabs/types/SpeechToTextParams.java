package ai.runapi.elevenlabs.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for speech to text operations. */
public final class SpeechToTextParams {
  private final String sourceAudioUrl;
  private final String callbackUrl;
  private final String languageCode;
  private final Boolean tagAudioEvents;
  private final Boolean diarize;
  private final String model;

  private SpeechToTextParams(Builder builder) {
    this.sourceAudioUrl = ElevenlabsParamUtils.requireNonBlank(builder.sourceAudioUrl, "sourceAudioUrl");
    this.callbackUrl = builder.callbackUrl;
    this.languageCode = builder.languageCode;
    this.tagAudioEvents = builder.tagAudioEvents;
    this.diarize = builder.diarize;
    this.model = builder.model;
  }

  /** Creates a new SpeechToTextParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "elevenlabs/speech-to-text";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("source_audio_url", ElevenlabsParamUtils.wireValue(sourceAudioUrl));
    raw.put("callback_url", ElevenlabsParamUtils.wireValue(callbackUrl));
    raw.put("language_code", ElevenlabsParamUtils.wireValue(languageCode));
    raw.put("tag_audio_events", ElevenlabsParamUtils.wireValue(tagAudioEvents));
    raw.put("diarize", ElevenlabsParamUtils.wireValue(diarize));
    raw.put("model", ElevenlabsParamUtils.wireValue(model));
    return ElevenlabsParamUtils.compact(raw);
  }



  /** Builder for {@link SpeechToTextParams}. */
  public static final class Builder {
    private String sourceAudioUrl;
    private String callbackUrl;
    private String languageCode;
    private Boolean tagAudioEvents;
    private Boolean diarize;
    private String model;

    private Builder() {}

    /** Sets the source audio URL. */
    public Builder sourceAudioUrl(String value) {
      this.sourceAudioUrl = ElevenlabsParamUtils.requireNonBlank(value, "sourceAudioUrl");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = ElevenlabsParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Sets the language code. */
    public Builder languageCode(String value) {
      this.languageCode = ElevenlabsParamUtils.requireNonBlank(value, "languageCode");
      return this;
    }

    /** Sets the tag audio events. */
    public Builder tagAudioEvents(boolean value) {
      this.tagAudioEvents = value;
      return this;
    }

    /** Sets the diarize. */
    public Builder diarize(boolean value) {
      this.diarize = value;
      return this;
    }

    /** Sets the model slug using a typed model value. */
    public Builder model(SpeechToTextModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = ElevenlabsParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }

    /** Builds immutable speech to text parameters. */
    public SpeechToTextParams build() {
      return new SpeechToTextParams(this);
    }
  }
}
