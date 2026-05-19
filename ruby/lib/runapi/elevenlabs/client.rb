# frozen_string_literal: true

module RunApi
  module Elevenlabs
    class Client
      attr_reader :text_to_speech, :text_to_dialogue, :text_to_sound, :speech_to_text, :isolate_audio

      def initialize(api_key: nil, **options)
        @api_key = Core::Auth.resolve_api_key(api_key)

        client_options = Core::ClientOptions.new(api_key: @api_key, **options)
        http = client_options.http_client || Core::HttpClient.new(client_options)

        @text_to_speech = Resources::TextToSpeech.new(http)
        @text_to_dialogue = Resources::TextToDialogue.new(http)
        @text_to_sound = Resources::TextToSound.new(http)
        @speech_to_text = Resources::SpeechToText.new(http)
        @isolate_audio = Resources::IsolateAudio.new(http)
      end
    end
  end
end
