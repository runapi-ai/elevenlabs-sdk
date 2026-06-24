package ai.runapi.elevenlabs.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for isolate audio operations. */
public final class IsolateAudioParams {
  private final String sourceAudioUrl;
  private final String callbackUrl;
  private final String model;

  private IsolateAudioParams(Builder builder) {
    this.sourceAudioUrl = ElevenlabsParamUtils.requireNonBlank(builder.sourceAudioUrl, "sourceAudioUrl");
    this.callbackUrl = builder.callbackUrl;
    this.model = builder.model;
  }

  /** Creates a new IsolateAudioParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "elevenlabs/isolate-audio";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("source_audio_url", ElevenlabsParamUtils.wireValue(sourceAudioUrl));
    raw.put("callback_url", ElevenlabsParamUtils.wireValue(callbackUrl));
    raw.put("model", ElevenlabsParamUtils.wireValue(model));
    return ElevenlabsParamUtils.compact(raw);
  }



  /** Builder for {@link IsolateAudioParams}. */
  public static final class Builder {
    private String sourceAudioUrl;
    private String callbackUrl;
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

    /** Sets the model slug using a typed model value. */
    public Builder model(IsolateAudioModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = ElevenlabsParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }

    /** Builds immutable isolate audio parameters. */
    public IsolateAudioParams build() {
      return new IsolateAudioParams(this);
    }
  }
}
