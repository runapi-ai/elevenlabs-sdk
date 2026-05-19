# frozen_string_literal: true

module RunApi
  module Elevenlabs
    module Resources
      class TextToSound
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/elevenlabs/text_to_sound"
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
          raise Core::ValidationError, "text is required" unless param(params, :text)
          if param(params, :output_format) && !Types::TEXT_TO_SOUND_OUTPUT_FORMATS.include?(param(params, :output_format))
            raise Core::ValidationError, "Invalid output_format"
          end
          request(:post, ENDPOINT, body: params)
        end

        def get(id)
          request(:get, "#{ENDPOINT}/#{id}")
        end
      end
    end
  end
end
