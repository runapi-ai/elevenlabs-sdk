package ai.runapi.elevenlabs.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to speech operations. */
public final class TextToSpeechModel extends ElevenlabsValue {
  /** text-to-speech-multilingual-v2 model slug. */
  public static final TextToSpeechModel TEXT_TO_SPEECH_MULTILINGUAL_V2 = new TextToSpeechModel("text-to-speech-multilingual-v2");
  /** text-to-speech-turbo-v2.5 model slug. */
  public static final TextToSpeechModel TEXT_TO_SPEECH_TURBO_V2_5 = new TextToSpeechModel("text-to-speech-turbo-v2.5");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToSpeechModel(String value) {
    super(value);
  }
}
