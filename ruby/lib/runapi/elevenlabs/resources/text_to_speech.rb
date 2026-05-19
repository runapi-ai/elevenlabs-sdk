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
          model = param(params, :model)
          raise Core::ValidationError, "model is required" unless model
          raise Core::ValidationError, "Invalid model: #{model}" unless Types::TEXT_TO_SPEECH_MODELS.include?(model)
          raise Core::ValidationError, "text is required" unless param(params, :text)
        end
      end
    end
  end
end
