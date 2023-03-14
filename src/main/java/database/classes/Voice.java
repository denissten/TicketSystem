package database.classes;

public class Voice {
    private int voice_id;
    private String tembr_voice;

    public Voice(){}

    public Voice(int voice_id, String tembr_voice) {
        this.voice_id = voice_id;
        this.tembr_voice = tembr_voice;
    }

    public int getVoice_id() {
        return voice_id;
    }

    public void setVoice_id(int voice_id) {
        this.voice_id = voice_id;
    }

    public String getTembr_voice() {
        return tembr_voice;
    }

    public void setTembr_voice(String tembr_voice) {
        this.tembr_voice = tembr_voice;
    }
}
