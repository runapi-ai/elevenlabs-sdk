# frozen_string_literal: true

Dir.chdir(__dir__) do

  Gem::Specification.new do |spec|
    spec.name = "runapi-elevenlabs"
    spec.version = "0.2.6"
    spec.authors = ["RunAPI"]
    spec.email = ["contact@runapi.ai"]

    spec.summary = "Elevenlabs API Ruby SDK for RunAPI"
    spec.description = "The elevenlabs api Ruby SDK is the language-specific package for ElevenLabs on RunAPI. Use this elevenlabs api package for voice, dialogue, transcription, sound effect, and cleanup flows when your application needs JSON request bodies, task status lookup, and consistent RunAPI errors in Ruby."
    spec.homepage = "https://runapi.ai/models/elevenlabs"
    spec.license = "Apache-2.0"
    spec.required_ruby_version = ">= 3.1.0"
    spec.metadata["homepage_uri"] = "https://runapi.ai/models/elevenlabs"
    spec.metadata["documentation_uri"] = "https://github.com/runapi-ai/elevenlabs-sdk/blob/main/ruby/README.md"
    spec.metadata["source_code_uri"] = "https://github.com/runapi-ai/elevenlabs-sdk"
    spec.metadata["changelog_uri"] = "https://github.com/runapi-ai/elevenlabs-sdk/blob/main/CHANGELOG.md"



    spec.files = Dir.glob("lib/**/*") + %w[LICENSE README.md]
    spec.extra_rdoc_files = ["README.md"]
        spec.require_paths = ["lib"]

    spec.add_dependency "runapi-core", "~> 0.2.5"
  end
end
