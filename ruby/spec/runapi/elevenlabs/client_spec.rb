# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Elevenlabs::Client do
  before do
    allow(ConnectionPool).to receive(:new).and_return(instance_double(ConnectionPool))
  end

  after { RunApi.api_key = nil }

  it "accepts api_key as parameter" do
    client = described_class.new(api_key: "param-key")
    expect(client).to be_a(described_class)
  end

  it "falls back to global RunApi.api_key" do
    RunApi.api_key = "global-key"
    client = described_class.new
    expect(client).to be_a(described_class)
  end

  it "exposes all resource accessors" do
    client = described_class.new(api_key: "test-key")
    expect(client.text_to_speech).to be_a(RunApi::Elevenlabs::Resources::TextToSpeech)
    expect(client.text_to_dialogue).to be_a(RunApi::Elevenlabs::Resources::TextToDialogue)
    expect(client.text_to_sound).to be_a(RunApi::Elevenlabs::Resources::TextToSound)
    expect(client.speech_to_text).to be_a(RunApi::Elevenlabs::Resources::SpeechToText)
    expect(client.isolate_audio).to be_a(RunApi::Elevenlabs::Resources::IsolateAudio)
  end
end
