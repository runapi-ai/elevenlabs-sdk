package ai.runapi.elevenlabs.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for isolate audio operations. */
public final class IsolateAudioModel extends ElevenlabsValue {
  /** audio-isolation model slug. */
  public static final IsolateAudioModel AUDIO_ISOLATION = new IsolateAudioModel("audio-isolation");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public IsolateAudioModel(String value) {
    super(value);
  }
}
