package elevenlabs

type SpeechModel string

type SoundEffectOutputFormat string

type TaskStatus string

const (
	ModelTTSTurbo        SpeechModel = "text-to-speech-turbo-2-5"
	ModelTTSMultilingual SpeechModel = "text-to-speech-multilingual-v2"

	OutputMP344100128 SoundEffectOutputFormat = "mp3_44100_128"
)

type AsyncTaskResponse struct {
	ID     string     `json:"id"`
	Status TaskStatus `json:"status"`
	Error  string     `json:"error,omitempty"`
}

func (r AsyncTaskResponse) GetID() string     { return r.ID }
func (r AsyncTaskResponse) GetStatus() string { return string(r.Status) }
func (r AsyncTaskResponse) GetError() string  { return r.Error }

type AudioFile struct {
	URL string `json:"url"`
}

type AudioTaskResponse struct {
	AsyncTaskResponse
	Audios []AudioFile `json:"audios,omitempty"`
}

type TextToSpeechParams struct {
	Model           SpeechModel `json:"model" help:"required; text-to-speech-turbo-2-5 or text-to-speech-multilingual-v2"`
	Text            string      `json:"text" help:"required; max 5000 chars"`
	Voice           string      `json:"voice,omitempty" help:"optional; required for multilingual model"`
	CallbackURL     string      `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	Stability       *float64    `json:"stability,omitempty" help:"optional; 0-1"`
	SimilarityBoost *float64    `json:"similarity_boost,omitempty" help:"optional; 0-1"`
	Style           *float64    `json:"style,omitempty" help:"optional; 0-1"`
	Speed           *float64    `json:"speed,omitempty" help:"optional; 0.7-1.2"`
	Timestamps      *bool       `json:"timestamps,omitempty" help:"optional; return word timestamps"`
	PreviousText    string      `json:"previous_text,omitempty" help:"optional; max 5000 chars"`
	NextText        string      `json:"next_text,omitempty" help:"optional; max 5000 chars"`
	LanguageCode    string      `json:"language_code,omitempty" help:"optional; language code"`
}

type DialogueLine struct {
	Text  string `json:"text"`
	Voice string `json:"voice"`
}

type TextToDialogueParams struct {
	Dialogue     []DialogueLine `json:"dialogue" help:"required; dialogue lines with text and voice"`
	CallbackURL  string         `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	Stability    *float64       `json:"stability,omitempty" help:"optional; 0, 0.5, or 1"`
	LanguageCode string         `json:"language_code,omitempty" help:"optional; language code"`
}

type TextToSoundParams struct {
	Text            string                  `json:"text" help:"required; max 5000 chars"`
	CallbackURL     string                  `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	Loop            *bool                   `json:"loop,omitempty" help:"optional; loop the generated sound"`
	DurationSeconds *float64                `json:"duration_seconds,omitempty" help:"optional; 0.5-22"`
	PromptInfluence *float64                `json:"prompt_influence,omitempty" help:"optional; 0-1"`
	OutputFormat    SoundEffectOutputFormat `json:"output_format,omitempty" help:"optional; output codec and bitrate"`
}

type SpeechToTextParams struct {
	AudioURL       string `json:"audio_url" help:"required; uploaded audio URL"`
	CallbackURL    string `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
	LanguageCode   string `json:"language_code,omitempty" help:"optional; language code hint"`
	TagAudioEvents *bool  `json:"tag_audio_events,omitempty" help:"optional; tag laughter/applause/etc"`
	Diarize        *bool  `json:"diarize,omitempty" help:"optional; label speakers"`
}

type SpeechToTextResponse struct {
	AsyncTaskResponse
	Text string `json:"text,omitempty"`
}

type IsolateAudioParams struct {
	AudioURL    string `json:"audio_url" help:"required; uploaded audio URL"`
	CallbackURL string `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
}
