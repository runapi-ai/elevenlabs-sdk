package ai.runapi.elevenlabs.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Nested request item for typed parameter builders. */
public final class DialogueLine {
  private final String text;
  private final String voice;

  private DialogueLine(Builder builder) {
    this.text = ElevenlabsParamUtils.requireNonBlank(builder.text, "text");
    this.voice = ElevenlabsParamUtils.requireNonBlank(builder.voice, "voice");
  }

  /** Creates a new DialogueLine builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the line text. */
  public String getText() {
    return text;
  }

  /** Returns the voice identifier. */
  public String getVoice() {
    return voice;
  }

  Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("text", ElevenlabsParamUtils.wireValue(text));
    raw.put("voice", ElevenlabsParamUtils.wireValue(voice));
    return ElevenlabsParamUtils.compact(raw);
  }

  /** Builder for {@link DialogueLine}. */
  public static final class Builder {
    private String text;
    private String voice;

    private Builder() {}

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

    /** Builds an immutable DialogueLine. */
    public DialogueLine build() {
      return new DialogueLine(this);
    }
  }
}
