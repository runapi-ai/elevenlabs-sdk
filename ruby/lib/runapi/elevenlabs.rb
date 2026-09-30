# frozen_string_literal: true

require "runapi/core"
require_relative "elevenlabs/types"
require_relative "elevenlabs/resources/text_to_speech"
require_relative "elevenlabs/resources/text_to_dialogue"
require_relative "elevenlabs/resources/text_to_sound"
require_relative "elevenlabs/resources/speech_to_text"
require_relative "elevenlabs/resources/isolate_audio"
require_relative "elevenlabs/client"

module RunApi
  module Elevenlabs
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
    TaskFailedError = RunApi::Core::TaskFailedError
    TaskTimeoutError = RunApi::Core::TaskTimeoutError
  end
end
