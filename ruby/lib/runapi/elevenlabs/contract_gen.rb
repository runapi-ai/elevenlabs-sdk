# frozen_string_literal: true

module RunApi
  module Elevenlabs
    CONTRACT = {
      "isolate-audio" => {
        "models" => ["audio-isolation"],
        "fields_by_model" => {
          "audio-isolation" => {
            "source_audio_url" => {
              "required" => true
            }
          }
        }
      },
      "speech-to-text" => {
        "models" => ["speech-to-text"],
        "fields_by_model" => {
          "speech-to-text" => {
            "source_audio_url" => {
              "required" => true
            }
          }
        }
      },
      "text-to-dialogue" => {
        "models" => ["text-to-dialogue-v3"],
        "fields_by_model" => {
          "text-to-dialogue-v3" => {
            "dialogue" => {
              "required" => true
            },
            "stability" => {
              "enum" => [0.0, 0.5, 1.0]
            }
          }
        }
      },
      "text-to-sound" => {
        "models" => ["sound-effect-v2"],
        "fields_by_model" => {
          "sound-effect-v2" => {
            "output_format" => {
              "enum" => ["mp3_22050_32", "mp3_44100_32", "mp3_44100_64", "mp3_44100_96", "mp3_44100_128", "mp3_44100_192", "pcm_8000", "pcm_16000", "pcm_22050", "pcm_24000", "pcm_44100", "pcm_48000", "ulaw_8000", "alaw_8000", "opus_48000_32", "opus_48000_64", "opus_48000_96", "opus_48000_128", "opus_48000_192"]
            },
            "text" => {
              "required" => true
            }
          }
        }
      },
      "text-to-speech" => {
        "models" => ["text-to-speech-multilingual-v2", "text-to-speech-turbo-v2.5"],
        "fields_by_model" => {
          "text-to-speech-multilingual-v2" => {
            "model" => {
              "required" => true
            },
            "text" => {
              "required" => true
            }
          },
          "text-to-speech-turbo-v2.5" => {
            "model" => {
              "required" => true
            },
            "text" => {
              "required" => true
            }
          }
        },
        "rules" => [{
          "when" => {
            "model" => "text-to-speech-multilingual-v2"
          },
          "required" => ["voice"]
        }]
      }
    }.freeze
  end
end
