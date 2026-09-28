// Package elevenlabs provides the ElevenLabs audio API client.
package elevenlabs

import "github.com/runapi-ai/core-sdk/go/core"

// SpeechModel selects the speech synthesis engine. See [ModelTTSTurbo] and [ModelTTSMultilingual].
type SpeechModel string

// SoundEffectOutputFormat controls audio encoding and bitrate for TextToSound output.
type SoundEffectOutputFormat string

// TaskStatus is the async task lifecycle state (e.g. "processing", "completed", "failed").
type TaskStatus string

const (
	// ModelTTSTurbo is the low-latency model. Voice is optional (defaults to a preset).
	ModelTTSTurbo SpeechModel = "text-to-speech-turbo-v2.5"
	// ModelTTSMultilingual supports 29 languages. Voice is required.
	ModelTTSMultilingual SpeechModel = "text-to-speech-multilingual-v2"

	// OutputMP344100128 selects MP3 at 44100 Hz / 128 kbps.
	OutputMP344100128 SoundEffectOutputFormat = "mp3_44100_128"
)

// AsyncTaskResponse carries the task ID, lifecycle status, and error for all ElevenLabs async operations.
type AsyncTaskResponse struct {
	Usage *core.TaskUsage `json:"usage,omitempty"`
	ID     string     `json:"id"`
	Status TaskStatus `json:"status"`
	Error  string     `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return string(r.Status) }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

// AudioFile holds a URL to a generated audio file.
type AudioFile struct {
	URL string `json:"url"`
}

// AudioTaskResponse is the result of speech, dialogue, sound effect, or vocal isolation tasks.
type AudioTaskResponse struct {
	AsyncTaskResponse
	Audios []AudioFile `json:"audios,omitempty"`
}

// TextToSpeechParams configures speech generation.
// Voice is optional for [ModelTTSTurbo] (uses a preset) but required for [ModelTTSMultilingual].
// Set PreviousText/NextText to improve prosody across consecutive segments.
type TextToSpeechParams struct {
	Model           SpeechModel `json:"model" help:"required; model slug"`
	Text            string      `json:"text" help:"required; max 5000 chars"`
	Voice           string      `json:"voice,omitempty" help:"optional for turbo, required for multilingual; accepts a voice name or voice ID; turbo defaults to EkK5I93UQWFDigLMpZcX"`
	CallbackURL     string      `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	Stability       *float64    `json:"stability,omitempty" help:"optional; voice stability"`
	SimilarityBoost *float64    `json:"similarity_boost,omitempty" help:"optional; 0-1"`
	Style           *float64    `json:"style,omitempty" help:"optional; style preset"`
	Speed           *float64    `json:"speed,omitempty" help:"optional; 0.7-1.2"`
	Timestamps      *bool       `json:"timestamps,omitempty" help:"optional; return word timestamps"`
	PreviousText    string      `json:"previous_text,omitempty" help:"optional; max 5000 chars"`
	NextText        string      `json:"next_text,omitempty" help:"optional; max 5000 chars"`
	LanguageCode    string      `json:"language_code,omitempty" help:"optional; language code"`
}

// DialogueLine is a single speaker turn: text to speak and which voice to use.
type DialogueLine struct {
	Text  string `json:"text"`
	Voice string `json:"voice"`
}

// TextToDialogueParams configures multi-speaker dialogue synthesis.
// Each DialogueLine can use a different voice. Model is not specified; the service selects automatically.
type TextToDialogueParams struct {
	Dialogue     []DialogueLine `json:"dialogue" help:"required; dialogue lines with text and voice"`
	CallbackURL  string         `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	Stability    *float64       `json:"stability,omitempty" help:"optional; voice stability"`
	LanguageCode string         `json:"language_code,omitempty" help:"optional; language code"`
}

// TextToSoundParams configures sound effect generation (not speech — use [TextToSpeechParams] for speech).
// DurationSeconds controls length (0.5–22s). Set Loop to true for seamless looping audio.
type TextToSoundParams struct {
	Text            string                  `json:"text" help:"required; max 5000 chars"`
	CallbackURL     string                  `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	Loop            *bool                   `json:"loop,omitempty" help:"optional; loop the generated sound"`
	DurationSeconds *float64                `json:"duration_seconds,omitempty" help:"optional; 0.5-22"`
	PromptInfluence *float64                `json:"prompt_influence,omitempty" help:"optional; 0-1"`
	OutputFormat    SoundEffectOutputFormat `json:"output_format,omitempty" help:"optional; output format"`
}

// SpeechToTextParams configures audio transcription.
// Set Diarize to label distinct speakers; set TagAudioEvents to annotate laughter, applause, etc.
type SpeechToTextParams struct {
	SourceAudioURL string `json:"source_audio_url" help:"required; source audio URL"`
	CallbackURL    string `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	LanguageCode   string `json:"language_code,omitempty" help:"optional; language code hint"`
	TagAudioEvents *bool  `json:"tag_audio_events,omitempty" help:"optional; tag laughter/applause/etc"`
	Diarize        *bool  `json:"diarize,omitempty" help:"optional; label speakers"`
}

// SpeechToTextResponse carries the transcribed text. Only populated when the task completes.
type SpeechToTextResponse struct {
	AsyncTaskResponse
	Text string `json:"text,omitempty"`
}

// IsolateAudioParams configures vocal isolation. Only requires the source audio URL.
type IsolateAudioParams struct {
	SourceAudioURL string `json:"source_audio_url" help:"required; source audio URL"`
	CallbackURL    string `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
}
