package ai.runapi.elevenlabs.types;

import ai.runapi.core.types.RunApiValue;

abstract class ElevenlabsValue extends RunApiValue {
  ElevenlabsValue(String value) {
    super(value);
  }
}
