# frozen_string_literal: true

module RunApi
  module Elevenlabs
    module Resources
      class SpeechToText
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/elevenlabs/speech_to_text"
        RESPONSE_CLASS = Types::SpeechToTextResponse
        COMPLETED_RESPONSE_CLASS = Types::CompletedSpeechToTextResponse

        def initialize(http)
          @http = http
        end

        def run(**params)
          task = create(**params)
          poll_until_complete { get(task.id) }
        end

        def create(**params)
          params = compact_params(params)
          raise Core::ValidationError, "audio_url is required" unless param(params, :audio_url)
          request(:post, ENDPOINT, body: params)
        end

        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end
      end
    end
  end
end
