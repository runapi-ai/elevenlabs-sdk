package ai.runapi.elevenlabs.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for speech to text operations. */
public final class SpeechToTextModel extends ElevenlabsValue {
  /** speech-to-text model slug. */
  public static final SpeechToTextModel SPEECH_TO_TEXT = new SpeechToTextModel("speech-to-text");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public SpeechToTextModel(String value) {
    super(value);
  }
}
