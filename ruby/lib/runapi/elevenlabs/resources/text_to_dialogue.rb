# frozen_string_literal: true

module RunApi
  module Elevenlabs
    module Resources
      class TextToDialogue
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/elevenlabs/text_to_dialogue"
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
          raise Core::ValidationError, "dialogue is required" unless param(params, :dialogue)
          request(:post, ENDPOINT, body: params)
        end

        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end
      end
    end
  end
end
