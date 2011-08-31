package pilotage.sound;

import pilotage.util.SimpleFileBuffer;

/**
 * A simple implementation of the abstract class <tt>FileBuffer</tt>.
 * It used a <tt>HashMap</tt> as container of datum
 * 
 */
public class SoundFileBuffer extends SimpleFileBuffer {

	public SoundFileBuffer(int sizeMax) {
		super(sizeMax);
	}
	
	public synchronized void load(String fileName) {
		if(super.isFull())
			super.removeOlder();
		if(isMidi(fileName))
			super.datum.put(fileName, new MidiFile(fileName));
		else if(isWave(fileName))
			super.datum.put(fileName, new WaveFile(fileName));
		else
			System.err.println(fileName + " is not a supported audio file");
	}
	
    private boolean isMidi(String fileName) {
    	return fileName.matches("^.*\\x2emid$");
    }

    private boolean isWave(String fileName) {
    	return fileName.matches("^.*\\x2ewav$");
    }
}
