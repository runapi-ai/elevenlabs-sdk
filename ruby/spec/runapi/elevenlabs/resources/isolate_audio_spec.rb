# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Elevenlabs::Resources::IsolateAudio do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }

  it "creates isolate-audio tasks" do
    expect(http).to receive(:request).with(:post, "/api/v1/elevenlabs/isolate_audio", body: {
      source_audio_url: "https://file.runapi.ai/source.mp3"
    }).and_return("id" => "task-5", "status" => "processing")

    result = resource.create(source_audio_url: "https://file.runapi.ai/source.mp3")
    expect(result.id).to eq("task-5")
  end

  it "gets isolate-audio tasks" do
    expect(http).to receive(:request).with(:get, "/api/v1/elevenlabs/isolate_audio/task-5").and_return(
      "id" => "task-5", "status" => "completed", "audios" => [{"url" => "https://file.runapi.ai/audio.mp3"}]
    )

    result = resource.get("task-5")
    expect(result.audios.first.url).to eq("https://file.runapi.ai/audio.mp3")
  end
end
