# frozen_string_literal: true

require "runapi/elevenlabs"

client = RunApi::Elevenlabs::Client.new(api_key: ENV.fetch("RUNAPI_API_KEY"))
result = client.text_to_speech.run(
  model: "text-to-speech-turbo-2-5",
  text: "Hello from RunAPI",
  voice: "Rachel"
)

puts result.audios&.first&.url
