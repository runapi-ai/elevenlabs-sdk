# frozen_string_literal: true

module RunApi
  module Elevenlabs
    # ElevenLabs audio API client for speech synthesis, multi-speaker dialogue,
    # sound effects, transcription, and vocal isolation.
    #
    # @example
    #   client = RunApi::Elevenlabs::Client.new(api_key: "sk-...")
    #   result = client.text_to_speech.run(
    #     model: "text-to-speech-turbo-v2.5",
    #     text: "Hello, world!"
    #   )
    #   puts result.audios.first.url
    class Client < RunApi::Core::Client
      # @return [Resources::TextToSpeech] Single-speaker speech synthesis operations.
      attr_reader :text_to_speech
      # @return [Resources::TextToDialogue] Multi-speaker dialogue synthesis operations.
      attr_reader :text_to_dialogue
      # @return [Resources::TextToSound] Sound effect generation operations.
      attr_reader :text_to_sound
      # @return [Resources::SpeechToText] Audio transcription operations.
      attr_reader :speech_to_text
      # @return [Resources::IsolateAudio] Vocal isolation operations.
      attr_reader :isolate_audio

      def initialize(api_key: nil, **options)
        super

        @text_to_speech = Resources::TextToSpeech.new(http)
        @text_to_dialogue = Resources::TextToDialogue.new(http)
        @text_to_sound = Resources::TextToSound.new(http)
        @speech_to_text = Resources::SpeechToText.new(http)
        @isolate_audio = Resources::IsolateAudio.new(http)
      end
    end
  end
end
