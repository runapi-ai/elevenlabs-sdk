# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Elevenlabs::Resources::TextToSound do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }

  it "posts text-to-sound requests" do
    params = {text: "Boom", output_format: "mp3_44100_128"}
    expect(http).to receive(:request).with(:post, "/api/v1/elevenlabs/text_to_sound", body: params).and_return("id" => "task-3")

    result = resource.create(**params)
    expect(result.id).to eq("task-3")
  end
end
