// Package elevenlabs provides the ElevenLabs audio API client.
//
//	client, err := elevenlabs.NewClient(option.WithAPIKey("sk-your-api-key"))
//	result, err := client.TextToSpeech.Run(ctx, elevenlabs.TextToSpeechParams{
//	    Model: elevenlabs.ModelTTSTurbo, Text: "Hello, world!",
//	})
package elevenlabs

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const (
	textToSpeechPath   = "/api/v1/elevenlabs/text_to_speech"
	textToDialoguePath = "/api/v1/elevenlabs/text_to_dialogue"
	textToSoundPath    = "/api/v1/elevenlabs/text_to_sound"
	speechToTextPath   = "/api/v1/elevenlabs/speech_to_text"
	isolateAudioPath   = "/api/v1/elevenlabs/isolate_audio"
)

// Client provides speech synthesis, sound effects, transcription, and vocal isolation.
type Client struct {
	base.Base
	TextToSpeech   *TextToSpeech
	TextToDialogue *TextToDialogue
	TextToSound    *TextToSound
	SpeechToText   *SpeechToText
	IsolateAudio   *IsolateAudio
}

// NewClient creates an ElevenLabs client with the given options.
func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

// NewClientWithHTTP creates an ElevenLabs client with a pre-configured HTTP transport.
func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{
		Base:           base.New(httpClient),
		TextToSpeech:   &TextToSpeech{http: httpClient},
		TextToDialogue: &TextToDialogue{http: httpClient},
		TextToSound:    &TextToSound{http: httpClient},
		SpeechToText:   &SpeechToText{http: httpClient},
		IsolateAudio:   &IsolateAudio{http: httpClient},
	}
}

// TextToSpeech generates single-speaker speech with configurable voice, speed, and language.
// Voice is optional for ModelTTSTurbo (uses a preset) but required for ModelTTSMultilingual.
type TextToSpeech struct{ http core.HTTPClient }

// TextToDialogue generates multi-speaker audio where each DialogueLine can use a different voice.
type TextToDialogue struct{ http core.HTTPClient }

// TextToSound generates sound effects (rain, footsteps, ambience, etc.) from text descriptions, not speech.
type TextToSound struct{ http core.HTTPClient }

// SpeechToText transcribes audio with optional speaker diarization and audio event tagging (laughter, applause).
type SpeechToText struct{ http core.HTTPClient }

// IsolateAudio separates vocals from background noise, returning a clean vocal-only audio track.
type IsolateAudio struct{ http core.HTTPClient }

// Create submits an ElevenLabs text-to-speech task and returns immediately with a task id.
func (r *TextToSpeech) Create(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToSpeechPath, core.CompactParams(params), requestOptions)
}

// Get fetches the current status of an ElevenLabs text-to-speech task by id.
func (r *TextToSpeech) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToSpeechPath, id), requestOptions)
}

// Run submits an ElevenLabs text-to-speech task and polls until it completes.
func (r *TextToSpeech) Run(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// Create submits an ElevenLabs text-to-dialogue task and returns immediately with a task id.
func (r *TextToDialogue) Create(ctx context.Context, params TextToDialogueParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToDialoguePath, core.CompactParams(params), requestOptions)
}

// Get fetches the current status of an ElevenLabs text-to-dialogue task by id.
func (r *TextToDialogue) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToDialoguePath, id), requestOptions)
}

// Run submits an ElevenLabs text-to-dialogue task and polls until it completes.
func (r *TextToDialogue) Run(ctx context.Context, params TextToDialogueParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// Create submits an ElevenLabs text-to-sound task and returns immediately with a task id.
func (r *TextToSound) Create(ctx context.Context, params TextToSoundParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToSoundPath, core.CompactParams(params), requestOptions)
}

// Get fetches the current status of an ElevenLabs text-to-sound task by id.
func (r *TextToSound) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToSoundPath, id), requestOptions)
}

// Run submits an ElevenLabs text-to-sound task and polls until it completes.
func (r *TextToSound) Run(ctx context.Context, params TextToSoundParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// Create submits an ElevenLabs speech-to-text task and returns immediately with a task id.
func (r *SpeechToText) Create(ctx context.Context, params SpeechToTextParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, speechToTextPath, core.CompactParams(params), requestOptions)
}

// Get fetches the current status of an ElevenLabs speech-to-text task by id.
func (r *SpeechToText) Get(ctx context.Context, id string, opts ...option.RequestOption) (*SpeechToTextResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[SpeechToTextResponse](ctx, r.http, core.ResourcePath(speechToTextPath, id), requestOptions)
}

// Run submits an ElevenLabs speech-to-text task and polls until it completes.
func (r *SpeechToText) Run(ctx context.Context, params SpeechToTextParams, opts ...option.RequestOption) (*SpeechToTextResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*SpeechToTextResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

// Create submits an ElevenLabs audio isolation task and returns immediately with a task id.
func (r *IsolateAudio) Create(ctx context.Context, params IsolateAudioParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, isolateAudioPath, core.CompactParams(params), requestOptions)
}

// Get fetches the current status of an ElevenLabs audio isolation task by id.
func (r *IsolateAudio) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(isolateAudioPath, id), requestOptions)
}

// Run submits an ElevenLabs audio isolation task and polls until it completes.
func (r *IsolateAudio) Run(ctx context.Context, params IsolateAudioParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}
