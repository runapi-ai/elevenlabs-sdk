package elevenlabs

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const (
	textToSpeechPath    = "/api/v1/elevenlabs/text_to_speech"
	textToDialoguePath = "/api/v1/elevenlabs/text_to_dialogue"
	textToSoundPath    = "/api/v1/elevenlabs/text_to_sound"
	speechToTextPath    = "/api/v1/elevenlabs/speech_to_text"
	isolateAudioPath    = "/api/v1/elevenlabs/isolate_audio"
)

type Client struct {
	TextToSpeech    *TextToSpeech
	TextToDialogue *TextToDialogue
	TextToSound    *TextToSound
	SpeechToText    *SpeechToText
	IsolateAudio    *IsolateAudio
}

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

func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{
		TextToSpeech:    &TextToSpeech{http: httpClient},
		TextToDialogue: &TextToDialogue{http: httpClient},
		TextToSound:    &TextToSound{http: httpClient},
		SpeechToText:    &SpeechToText{http: httpClient},
		IsolateAudio:    &IsolateAudio{http: httpClient},
	}
}

type TextToSpeech struct{ http core.HTTPClient }
type TextToDialogue struct{ http core.HTTPClient }
type TextToSound struct{ http core.HTTPClient }
type SpeechToText struct{ http core.HTTPClient }
type IsolateAudio struct{ http core.HTTPClient }

func (r *TextToSpeech) Create(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToSpeechPath, core.CompactParams(params), requestOptions)
}
func (r *TextToSpeech) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToSpeechPath, id), requestOptions)
}
func (r *TextToSpeech) Run(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

func (r *TextToDialogue) Create(ctx context.Context, params TextToDialogueParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToDialoguePath, core.CompactParams(params), requestOptions)
}
func (r *TextToDialogue) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToDialoguePath, id), requestOptions)
}
func (r *TextToDialogue) Run(ctx context.Context, params TextToDialogueParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

func (r *TextToSound) Create(ctx context.Context, params TextToSoundParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToSoundPath, core.CompactParams(params), requestOptions)
}
func (r *TextToSound) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToSoundPath, id), requestOptions)
}
func (r *TextToSound) Run(ctx context.Context, params TextToSoundParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

func (r *SpeechToText) Create(ctx context.Context, params SpeechToTextParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, speechToTextPath, core.CompactParams(params), requestOptions)
}
func (r *SpeechToText) Get(ctx context.Context, id string, opts ...option.RequestOption) (*SpeechToTextResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[SpeechToTextResponse](ctx, r.http, core.ResourcePath(speechToTextPath, id), requestOptions)
}
func (r *SpeechToText) Run(ctx context.Context, params SpeechToTextParams, opts ...option.RequestOption) (*SpeechToTextResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*SpeechToTextResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}

func (r *IsolateAudio) Create(ctx context.Context, params IsolateAudioParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, isolateAudioPath, core.CompactParams(params), requestOptions)
}
func (r *IsolateAudio) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(isolateAudioPath, id), requestOptions)
}
func (r *IsolateAudio) Run(ctx context.Context, params IsolateAudioParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx, func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) }, func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) }, pollingOptions)
}
