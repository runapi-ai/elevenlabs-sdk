package ai.runapi.elevenlabs.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to speech operations. */
public final class TextToSpeechParams {
  private final String model;
  private final String text;
  private final String voice;
  private final String callbackUrl;
  private final Double stability;
  private final Double similarityBoost;
  private final Double style;
  private final Double speed;
  private final Boolean timestamps;
  private final String previousText;
  private final String nextText;
  private final String languageCode;

  private TextToSpeechParams(Builder builder) {
    this.model = builder.model;
    this.text = ElevenlabsParamUtils.requireNonBlank(builder.text, "text");
    this.voice = builder.voice;
    this.callbackUrl = builder.callbackUrl;
    this.stability = builder.stability;
    this.similarityBoost = builder.similarityBoost;
    this.style = builder.style;
    this.speed = builder.speed;
    this.timestamps = builder.timestamps;
    this.previousText = builder.previousText;
    this.nextText = builder.nextText;
    this.languageCode = builder.languageCode;
  }

  /** Creates a new TextToSpeechParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "elevenlabs/text-to-speech";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", ElevenlabsParamUtils.wireValue(model));
    raw.put("text", ElevenlabsParamUtils.wireValue(text));
    raw.put("voice", ElevenlabsParamUtils.wireValue(voice));
    raw.put("callback_url", ElevenlabsParamUtils.wireValue(callbackUrl));
    raw.put("stability", ElevenlabsParamUtils.wireValue(stability));
    raw.put("similarity_boost", ElevenlabsParamUtils.wireValue(similarityBoost));
    raw.put("style", ElevenlabsParamUtils.wireValue(style));
    raw.put("speed", ElevenlabsParamUtils.wireValue(speed));
    raw.put("timestamps", ElevenlabsParamUtils.wireValue(timestamps));
    raw.put("previous_text", ElevenlabsParamUtils.wireValue(previousText));
    raw.put("next_text", ElevenlabsParamUtils.wireValue(nextText));
    raw.put("language_code", ElevenlabsParamUtils.wireValue(languageCode));
    return ElevenlabsParamUtils.compact(raw);
  }



  /** Builder for {@link TextToSpeechParams}. */
  public static final class Builder {
    private String model;
    private String text;
    private String voice;
    private String callbackUrl;
    private Double stability;
    private Double similarityBoost;
    private Double style;
    private Double speed;
    private Boolean timestamps;
    private String previousText;
    private String nextText;
    private String languageCode;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToSpeechModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = ElevenlabsParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }


    /** Sets the line text. */
    public Builder text(String value) {
      this.text = ElevenlabsParamUtils.requireNonBlank(value, "text");
      return this;
    }

    /** Sets the voice identifier. */
    public Builder voice(String value) {
      this.voice = ElevenlabsParamUtils.requireNonBlank(value, "voice");
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = ElevenlabsParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Sets the stability. */
    public Builder stability(double value) {
      this.stability = value;
      return this;
    }

    /** Sets the similarity boost. */
    public Builder similarityBoost(double value) {
      this.similarityBoost = value;
      return this;
    }

    /** Sets the style. */
    public Builder style(double value) {
      this.style = value;
      return this;
    }

    /** Sets the speed. */
    public Builder speed(double value) {
      this.speed = value;
      return this;
    }

    /** Sets the timestamps. */
    public Builder timestamps(boolean value) {
      this.timestamps = value;
      return this;
    }

    /** Sets the previous text. */
    public Builder previousText(String value) {
      this.previousText = ElevenlabsParamUtils.requireNonBlank(value, "previousText");
      return this;
    }

    /** Sets the next text. */
    public Builder nextText(String value) {
      this.nextText = ElevenlabsParamUtils.requireNonBlank(value, "nextText");
      return this;
    }

    /** Sets the language code. */
    public Builder languageCode(String value) {
      this.languageCode = ElevenlabsParamUtils.requireNonBlank(value, "languageCode");
      return this;
    }

    /** Builds immutable text to speech parameters. */
    public TextToSpeechParams build() {
      return new TextToSpeechParams(this);
    }
  }
}
