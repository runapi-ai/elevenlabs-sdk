# frozen_string_literal: true

module RunApi
  module Elevenlabs
    module Types
      TEXT_TO_SPEECH_MODELS = %w[text-to-speech-turbo-2-5 text-to-speech-multilingual-v2].freeze
      TEXT_TO_SOUND_OUTPUT_FORMATS = %w[
        mp3_22050_32 mp3_44100_32 mp3_44100_64 mp3_44100_96 mp3_44100_128 mp3_44100_192
        pcm_8000 pcm_16000 pcm_22050 pcm_24000 pcm_44100 pcm_48000
        ulaw_8000 alaw_8000
        opus_48000_32 opus_48000_64 opus_48000_96 opus_48000_128 opus_48000_192
      ].freeze

      class Audio < RunApi::Core::BaseModel
        optional :url, String
      end

      class AsyncTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
      end

      class AudioTaskResponse < AsyncTaskResponse
        optional :audios, [ -> { Audio } ]
        optional :error, String
      end

      class SpeechToTextResponse < AsyncTaskResponse
        optional :text, String
        optional :error, String
      end

      # Narrowed responses returned by `run()` methods once polling observes
      # `status: "completed"`. Result fields are required so consumers never
      # have to null-check them on a successful task.
      class CompletedAudioTaskResponse < AudioTaskResponse
        required :audios, [ -> { Audio } ]
      end

      class CompletedSpeechToTextResponse < SpeechToTextResponse
        required :text, String
      end
    end
  end
end
