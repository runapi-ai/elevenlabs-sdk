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

        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        def create(options: nil, **params)
          params = compact_params(params)
          raise Core::ValidationError, "source_audio_url is required" unless param(params, :source_audio_url)
          request(:post, ENDPOINT, body: params, options: options)
        end

        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end
      end
    end
  end
end
