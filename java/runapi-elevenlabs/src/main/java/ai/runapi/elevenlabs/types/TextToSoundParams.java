package ai.runapi.elevenlabs.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to sound operations. */
public final class TextToSoundParams {
  private final String text;
  private final String callbackUrl;
  private final Boolean loop;
  private final Double durationSeconds;
  private final Double promptInfluence;
  private final String outputFormat;
  private final String model;

  private TextToSoundParams(Builder builder) {
    this.text = builder.text;
    this.callbackUrl = builder.callbackUrl;
    this.loop = builder.loop;
    this.durationSeconds = builder.durationSeconds;
    this.promptInfluence = builder.promptInfluence;
    this.outputFormat = builder.outputFormat;
    this.model = builder.model;
  }

  /** Creates a new TextToSoundParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "elevenlabs/text-to-sound";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("text", ElevenlabsParamUtils.wireValue(text));
    raw.put("callback_url", ElevenlabsParamUtils.wireValue(callbackUrl));
    raw.put("loop", ElevenlabsParamUtils.wireValue(loop));
    raw.put("duration_seconds", ElevenlabsParamUtils.wireValue(durationSeconds));
    raw.put("prompt_influence", ElevenlabsParamUtils.wireValue(promptInfluence));
    raw.put("output_format", ElevenlabsParamUtils.wireValue(outputFormat));
    raw.put("model", ElevenlabsParamUtils.wireValue(model));
    return ElevenlabsParamUtils.compact(raw);
  }



  /** Builder for {@link TextToSoundParams}. */
  public static final class Builder {
    private String text;
    private String callbackUrl;
    private Boolean loop;
    private Double durationSeconds;
    private Double promptInfluence;
    private String outputFormat;
    private String model;

    private Builder() {}

    /** Sets the line text. */
    public Builder text(String value) {
      this.text = value;
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = value;
      return this;
    }

    /** Sets the loop. */
    public Builder loop(boolean value) {
      this.loop = value;
      return this;
    }

    /** Sets the duration in seconds. */
    public Builder durationSeconds(double value) {
      this.durationSeconds = value;
      return this;
    }

    /** Sets the prompt influence. */
    public Builder promptInfluence(double value) {
      this.promptInfluence = value;
      return this;
    }

    /** Sets the output format. */
    public Builder outputFormat(String value) {
      this.outputFormat = value;
      return this;
    }

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToSoundModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = value;
      return this;
    }

    /** Builds immutable text to sound parameters. */
    public TextToSoundParams build() {
      return new TextToSoundParams(this);
    }
  }
}
