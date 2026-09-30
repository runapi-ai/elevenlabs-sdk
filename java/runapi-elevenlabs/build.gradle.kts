plugins {
  `java-library`
  `maven-publish`
}

extra["runapiSlug"] = "elevenlabs"

description = "RunAPI ElevenLabs Java SDK for ElevenLabs workflows."

java {
  withSourcesJar()
  withJavadocJar()
}

dependencies {
  api("ai.runapi:runapi-core:0.9.0")

  testImplementation(platform("org.junit:junit-bom:5.10.3"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      from(components["java"])
      artifactId = "runapi-elevenlabs"
      pom {
        name = "RunAPI ElevenLabs Java SDK"
        description = "RunAPI ElevenLabs Java SDK for ElevenLabs workflows."
        url = "https://runapi.ai/models/elevenlabs"
        licenses {
          license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
          }
        }
        developers {
          developer {
            id = "runapi"
            name = "RunAPI"
            email = "contact@runapi.ai"
          }
        }
        scm {
          url = "https://github.com/runapi-ai/elevenlabs-sdk"
          connection = "scm:git:https://github.com/runapi-ai/elevenlabs-sdk.git"
          developerConnection = "scm:git:ssh://git@github.com/runapi-ai/elevenlabs-sdk.git"
        }
      }
    }
  }
}
