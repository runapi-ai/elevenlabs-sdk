package ai.runapi.elevenlabs.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to dialogue operations. */
public final class TextToDialogueModel extends ElevenlabsValue {
  /** text-to-dialogue-v3 model slug. */
  public static final TextToDialogueModel TEXT_TO_DIALOGUE_V3 = new TextToDialogueModel("text-to-dialogue-v3");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToDialogueModel(String value) {
    super(value);
  }
}
