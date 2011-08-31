package pilotage.sound;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class WaveFile extends File implements SoundFile {

	//final static long serialVersionUID = ???;
	
	public WaveFile(String fileName) {
    	super(fileName);
    }
	
    public void play() {

    	AudioInputStream au;
    	AudioFormat form;
    	DataLine.Info info;
    	SourceDataLine src;

    	try {
    		au = AudioSystem.getAudioInputStream(this);
    		form = au.getFormat();
    		info = new DataLine.Info(SourceDataLine.class, form);
    		src = (SourceDataLine) AudioSystem.getLine(info);
    		src.open(form);
    		src.start();
    		int read = 0;
    		byte[] audioData = new byte[16384];
    		while(read > -1) {
    			read = au.read(audioData, 0, audioData.length);
    			if(read >= 0) {
    				src.write(audioData,0,read);
    			}
    		}
    		src.drain();
    		src.close();
    	} catch(Exception e) {
    		System.out.println(e.toString());
    	}
    }

    public boolean isFinished() {
    	return true;
    }
}

