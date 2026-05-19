package elevenlabs

import (
	"context"
	"encoding/json"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method string
	path   string
	body   any
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method = method
	s.path = path
	if opts != nil {
		s.body = opts.Body
	}
	return json.RawMessage(`{"id":"task_123","status":"processing"}`), nil
}

func TestTextToSpeechCreate(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.TextToSpeech.Create(context.Background(), TextToSpeechParams{Model: ModelTTSTurbo, Text: "Hello", Voice: "Rachel"})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != "/api/v1/elevenlabs/text_to_speech" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["model"] != string(ModelTTSTurbo) || body["text"] != "Hello" {
		t.Fatalf("unexpected body: %v", body)
	}
}

func TestTextToDialogueCreate(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.TextToDialogue.Create(context.Background(), TextToDialogueParams{Dialogue: []DialogueLine{{Text: "Hello", Voice: "Adam"}}})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != "/api/v1/elevenlabs/text_to_dialogue" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
}

func TestTextToSoundCreate(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.TextToSound.Create(context.Background(), TextToSoundParams{Text: "Boom", OutputFormat: OutputMP344100128})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != "/api/v1/elevenlabs/text_to_sound" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
}

func TestSpeechToTextGet(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.SpeechToText.Get(context.Background(), "task_abc")
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != "/api/v1/elevenlabs/speech_to_text/task_abc" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
}

func TestIsolateAudioGet(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	_, err := client.IsolateAudio.Get(context.Background(), "task_xyz")
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != "/api/v1/elevenlabs/isolate_audio/task_xyz" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
}
