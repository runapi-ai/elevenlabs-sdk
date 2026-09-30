package ai.runapi.elevenlabs.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to dialogue operations. */
public final class TextToDialogueParams {
  private final List<DialogueLine> dialogue;
  private final String callbackUrl;
  private final Double stability;
  private final String languageCode;
  private final String model;

  private TextToDialogueParams(Builder builder) {
    this.dialogue = ElevenlabsParamUtils.list(builder.dialogue);
    this.callbackUrl = builder.callbackUrl;
    this.stability = builder.stability;
    this.languageCode = builder.languageCode;
    this.model = builder.model;
  }

  /** Creates a new TextToDialogueParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "elevenlabs/text-to-dialogue";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("dialogue", dialogueToMaps(dialogue));
    raw.put("callback_url", ElevenlabsParamUtils.wireValue(callbackUrl));
    raw.put("stability", ElevenlabsParamUtils.wireValue(stability));
    raw.put("language_code", ElevenlabsParamUtils.wireValue(languageCode));
    raw.put("model", ElevenlabsParamUtils.wireValue(model));
    return ElevenlabsParamUtils.compact(raw);
  }

  private static List<Map<String, Object>> dialogueToMaps(List<DialogueLine> values) {
    if (values == null) {
      return null;
    }
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (DialogueLine item : values) {
      result.add(item == null ? null : item.toMap());
    }
    return java.util.Collections.unmodifiableList(result);
  }

  /** Builder for {@link TextToDialogueParams}. */
  public static final class Builder {
    private List<DialogueLine> dialogue;
    private String callbackUrl;
    private Double stability;
    private String languageCode;
    private String model;

    private Builder() {}

    /** Sets the dialogue. */
    public Builder dialogue(List<DialogueLine> value) {
      this.dialogue = value;
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = value;
      return this;
    }

    /** Sets the stability. */
    public Builder stability(double value) {
      this.stability = value;
      return this;
    }

    /** Sets the language code. */
    public Builder languageCode(String value) {
      this.languageCode = value;
      return this;
    }

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToDialogueModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = value;
      return this;
    }

    /** Builds immutable text to dialogue parameters. */
    public TextToDialogueParams build() {
      return new TextToDialogueParams(this);
    }
  }
}
