# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Elevenlabs::Resources::SpeechToText do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }

  it "creates speech-to-text tasks" do
    expect(http).to receive(:request).with(:post, "/api/v1/elevenlabs/speech_to_text", body: {
      source_audio_url: "https://file.runapi.ai/source.mp3",
      diarize: true
    }).and_return("id" => "task-4", "status" => "processing")

    result = resource.create(source_audio_url: "https://file.runapi.ai/source.mp3", diarize: true)
    expect(result.id).to eq("task-4")
  end

  it "gets speech-to-text tasks" do
    expect(http).to receive(:request).with(:get, "/api/v1/elevenlabs/speech_to_text/task-4").and_return(
      "id" => "task-4", "status" => "completed", "text" => "Hello"
    )

    result = resource.get("task-4")
    expect(result.text).to eq("Hello")
  end
end
