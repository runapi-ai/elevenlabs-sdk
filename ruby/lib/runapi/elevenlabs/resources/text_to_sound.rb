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

        def run(options: nil, **params)
          task = create(options: options, **params)
          poll_until_complete { get(task.id, options: options) }
        end

        def create(options: nil, **params)
          params = compact_params(params)
          raise Core::ValidationError, "text is required" unless param(params, :text)
          if param(params, :output_format) && !Types::TEXT_TO_SOUND_OUTPUT_FORMATS.include?(param(params, :output_format))
            raise Core::ValidationError, "Invalid output_format"
          end
          request(:post, ENDPOINT, body: params, options: options)
        end

        def get(id, options: nil)
          request(:get, "#{ENDPOINT}/#{id}", options: options)
        end
      end
    end
  end
end
