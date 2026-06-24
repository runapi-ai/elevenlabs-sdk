# frozen_string_literal: true

module RunApi
  module Elevenlabs
    module Resources
      class TextToSpeech
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/elevenlabs/text_to_speech"
        RESPONSE_CLASS = Types::AudioTaskResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedAudioTaskResponse

        def initialize(http)
          @http = http
        end

        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        def create(**params)
          params = compact_params(params)
          validate_params!(params)
          request(:post, ENDPOINT, body: params)
        end

        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end

        private

        def validate_params!(params)
          validate_contract!(CONTRACT["text-to-speech"], params)
          if param(params, :model) == "text-to-speech-multilingual-v2" && !param(params, :voice)
            raise Core::ValidationError, "voice is required for text-to-speech-multilingual-v2"
          end
        end
      end
    end
  end
end
