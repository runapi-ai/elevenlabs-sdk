# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Elevenlabs::Resources::TextToDialogue do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }

  it "posts text-to-dialogue requests" do
    params = {dialogue: [{text: "Hello", voice: "Adam"}], stability: 0.5}
    expect(http).to receive(:request).with(:post, "/api/v1/elevenlabs/text_to_dialogue", body: params).and_return("id" => "task-2")

    result = resource.create(**params)
    expect(result.id).to eq("task-2")
  end
end
