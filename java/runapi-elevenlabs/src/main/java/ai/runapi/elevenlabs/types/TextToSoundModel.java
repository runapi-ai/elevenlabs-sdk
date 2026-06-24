package ai.runapi.elevenlabs.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to sound operations. */
public final class TextToSoundModel extends ElevenlabsValue {
  /** sound-effect-v2 model slug. */
  public static final TextToSoundModel SOUND_EFFECT_V2 = new TextToSoundModel("sound-effect-v2");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToSoundModel(String value) {
    super(value);
  }
}
