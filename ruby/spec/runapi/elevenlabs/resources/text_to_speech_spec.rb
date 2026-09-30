# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Elevenlabs::Resources::TextToSpeech do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }

  it "posts text-to-speech requests" do
    expect(http).to receive(:request).with(:post, "/api/v1/elevenlabs/text_to_speech", body: {
      model: "text-to-speech-turbo-v2.5",
      text: "Hello",
      voice: "EkK5I93UQWFDigLMpZcX"
    }).and_return("id" => "task-1")

    result = resource.create(model: "text-to-speech-turbo-v2.5", text: "Hello", voice: "EkK5I93UQWFDigLMpZcX")
    expect(result.id).to eq("task-1")
  end

  it "allows future voice ids without enum validation" do
    expect(http).to receive(:request).with(:post, "/api/v1/elevenlabs/text_to_speech", body: {
      model: "text-to-speech-multilingual-v2",
      text: "Hello",
      voice: "voice_future_2026_05"
    }).and_return("id" => "task-2")

    result = resource.create(model: "text-to-speech-multilingual-v2", text: "Hello", voice: "voice_future_2026_05")
    expect(result.id).to eq("task-2")
  end
end
