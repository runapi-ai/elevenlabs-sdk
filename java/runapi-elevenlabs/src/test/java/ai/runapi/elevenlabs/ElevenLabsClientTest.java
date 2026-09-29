package ai.runapi.elevenlabs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.elevenlabs.types.CompletedTextToSpeechResponse;
import ai.runapi.elevenlabs.types.TextToSpeechResponse;
import ai.runapi.elevenlabs.types.CompletedIsolateAudioResponse;
import ai.runapi.elevenlabs.types.CompletedSpeechToTextResponse;
import ai.runapi.elevenlabs.types.CompletedTextToDialogueResponse;
import ai.runapi.elevenlabs.types.CompletedTextToSoundResponse;
import ai.runapi.elevenlabs.types.CompletedTextToSpeechResponse;
import ai.runapi.elevenlabs.types.DialogueLine;
import ai.runapi.elevenlabs.types.IsolateAudioModel;
import ai.runapi.elevenlabs.types.IsolateAudioParams;
import ai.runapi.elevenlabs.types.IsolateAudioResponse;
import ai.runapi.elevenlabs.types.SpeechToTextModel;
import ai.runapi.elevenlabs.types.SpeechToTextParams;
import ai.runapi.elevenlabs.types.SpeechToTextResponse;
import ai.runapi.elevenlabs.types.TextToDialogueModel;
import ai.runapi.elevenlabs.types.TextToDialogueParams;
import ai.runapi.elevenlabs.types.TextToDialogueResponse;
import ai.runapi.elevenlabs.types.TextToSoundModel;
import ai.runapi.elevenlabs.types.TextToSoundParams;
import ai.runapi.elevenlabs.types.TextToSoundResponse;
import ai.runapi.elevenlabs.types.TextToSpeechModel;
import ai.runapi.elevenlabs.types.TextToSpeechParams;
import ai.runapi.elevenlabs.types.TextToSpeechResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class ElevenLabsClientTest {
  @Test
  void builderCreatesClientAndUniversalResources() {
    ElevenLabsClient client = ElevenLabsClient.builder().apiKey("sk-test").build();

    assertNotNull(client.textToSpeech());
    assertNotNull(client.files());
    assertNotNull(client.account());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new TextToSpeechModel("text-to-speech-turbo-v2.5"));

    assertEquals("\"text-to-speech-turbo-v2.5\"", json);
    assertEquals(new TextToSpeechModel("text-to-speech-turbo-v2.5"), Json.mapper().readValue(json, TextToSpeechModel.class));
  }

  @Test
  void createSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_123\",\"status\":\"processing\"}");
    ElevenLabsClient client = ElevenLabsClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToSpeech().create(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
            .text("sample")
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/elevenlabs/text_to_speech", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertNotNull(body);
  }

  @Test
  void getDecodesTaskResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_456\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    ElevenLabsClient client = ElevenLabsClient.builder().apiKey("sk-test").transport(transport).build();

    TextToSpeechResponse response = client.textToSpeech().get("task_456");

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/elevenlabs/text_to_speech/task_456", transport.request.getPath());
    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getAudios());
    assertEquals("kept", response.extraFields().get("custom").asText());
  }

  @Test
  void runPollsUntilCompletedAndKeepsExtraFields() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_789\",\"status\":\"processing\"}",
        "{\"id\":\"task_789\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    ElevenLabsClient client = ElevenLabsClient.builder().apiKey("sk-test").transport(transport).build();

    CompletedTextToSpeechResponse response = client.textToSpeech().run(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
            .text("sample")
            .build(),
        RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());

    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getAudios());
    assertEquals("kept", response.extraFields().get("custom").asText());
    assertEquals(2, transport.calls);
  }

  @Test
  void runRejectsCompletedResponseMissingResultField() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_missing\",\"status\":\"processing\"}",
        "{\"id\":\"task_missing\",\"status\":\"completed\"}");
    ElevenLabsClient client = ElevenLabsClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () -> client.textToSpeech().run(
                TextToSpeechParams.builder()
                    .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
                    .text("sample")
                    .build(),
            RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
  }

    @Test
    void coversIsolateaudioResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_isolate_audio\",\"status\":\"processing\"}");
      ElevenLabsClient createClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.isolateAudio().create(
              IsolateAudioParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_isolate_audio_options\",\"status\":\"processing\"}");
      ElevenLabsClient createWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.isolateAudio().create(
              IsolateAudioParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_isolate_audio\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.isolateAudio().get("task_isolate_audio"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_isolate_audio_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.isolateAudio().get("task_isolate_audio_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_isolate_audio_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_isolate_audio_run\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedIsolateAudioResponse runResponse = runClient.isolateAudio().run(
              IsolateAudioParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_isolate_audio_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_isolate_audio_run_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.isolateAudio().run(
              IsolateAudioParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversSpeechtotextResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_speech_to_text\",\"status\":\"processing\"}");
      ElevenLabsClient createClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.speechToText().create(
              SpeechToTextParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_speech_to_text_options\",\"status\":\"processing\"}");
      ElevenLabsClient createWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.speechToText().create(
              SpeechToTextParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_speech_to_text\",\"status\":\"completed\",\"text\":\"sample\"}");
      ElevenLabsClient getClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.speechToText().get("task_speech_to_text"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_speech_to_text_options\",\"status\":\"completed\",\"text\":\"sample\"}");
      ElevenLabsClient getWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.speechToText().get("task_speech_to_text_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_speech_to_text_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_speech_to_text_run\",\"status\":\"completed\",\"text\":\"sample\"}");
      ElevenLabsClient runClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedSpeechToTextResponse runResponse = runClient.speechToText().run(
              SpeechToTextParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_speech_to_text_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_speech_to_text_run_options\",\"status\":\"completed\",\"text\":\"sample\"}");
      ElevenLabsClient runWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.speechToText().run(
              SpeechToTextParams.builder()
                  .sourceAudioUrl("https://cdn.runapi.ai/public/samples/music.mp3")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversTexttodialogueResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_text_to_dialogue\",\"status\":\"processing\"}");
      ElevenLabsClient createClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.textToDialogue().create(
              TextToDialogueParams.builder()
                  .dialogue(java.util.Collections.singletonList(DialogueLine.builder().text("Hello").voice("Adam").build()))
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_dialogue_options\",\"status\":\"processing\"}");
      ElevenLabsClient createWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.textToDialogue().create(
              TextToDialogueParams.builder()
                  .dialogue(java.util.Collections.singletonList(DialogueLine.builder().text("Hello").voice("Adam").build()))
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_text_to_dialogue\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.textToDialogue().get("task_text_to_dialogue"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_dialogue_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.textToDialogue().get("task_text_to_dialogue_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_dialogue_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_dialogue_run\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedTextToDialogueResponse runResponse = runClient.textToDialogue().run(
              TextToDialogueParams.builder()
                  .dialogue(java.util.Collections.singletonList(DialogueLine.builder().text("Hello").voice("Adam").build()))
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_dialogue_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_dialogue_run_options\",\"status\":\"completed\",\"videos\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.textToDialogue().run(
              TextToDialogueParams.builder()
                  .dialogue(java.util.Collections.singletonList(DialogueLine.builder().text("Hello").voice("Adam").build()))
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversTexttosoundResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_text_to_sound\",\"status\":\"processing\"}");
      ElevenLabsClient createClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.textToSound().create(
              TextToSoundParams.builder()
                  .text("sample")
                  .durationSeconds(5.0)
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_sound_options\",\"status\":\"processing\"}");
      ElevenLabsClient createWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.textToSound().create(
              TextToSoundParams.builder()
                  .text("sample")
                  .durationSeconds(5.0)
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_text_to_sound\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.textToSound().get("task_text_to_sound"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_sound_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.textToSound().get("task_text_to_sound_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_sound_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_sound_run\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedTextToSoundResponse runResponse = runClient.textToSound().run(
              TextToSoundParams.builder()
                  .text("sample")
                  .durationSeconds(5.0)
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_sound_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_sound_run_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.textToSound().run(
              TextToSoundParams.builder()
                  .text("sample")
                  .durationSeconds(5.0)
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

    @Test
    void coversTexttospeechResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_text_to_speech\",\"status\":\"processing\"}");
      ElevenLabsClient createClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.textToSpeech().create(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
                  .text("sample")
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_speech_options\",\"status\":\"processing\"}");
      ElevenLabsClient createWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.textToSpeech().create(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
                  .text("sample")
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_text_to_speech\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.textToSpeech().get("task_text_to_speech"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_speech_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient getWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.textToSpeech().get("task_text_to_speech_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_speech_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_speech_run\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedTextToSpeechResponse runResponse = runClient.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
                  .text("sample")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_speech_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_speech_run_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      ElevenLabsClient runWithOptionsClient = ElevenLabsClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.TEXT_TO_SPEECH_TURBO_V2_5)
                  .text("sample")
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }
}
