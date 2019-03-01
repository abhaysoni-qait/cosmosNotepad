package cosmos;
import com.sun.speech.freetts.VoiceManager;

public class Voice implements Runnable{
		private String name;
		private com.sun.speech.freetts.Voice voice ;
		private String toSpeak;
		
		public Voice(String voiceName,String toSpeak) {
			this.name = voiceName;
			voice = VoiceManager.getInstance().getVoice(this.name);
			this.voice.allocate();
			this.toSpeak = toSpeak;
			
		}
		
		public void speak(String textToSpeak) {
			this.voice.speak(textToSpeak);
		}

		public void run() {
			speak(toSpeak);
		}
}
