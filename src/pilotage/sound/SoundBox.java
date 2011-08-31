package pilotage.sound;

import pilotage.Main;

/**
 * This is a singleton which play sound file.
 *
 */
public class SoundBox {

	private static SoundBox instance;
	
	private SoundFileBuffer cache;
	
	private SoundBox() {
		cache = new SoundFileBuffer(Main.configuration.cacheSize);
	}
	
	public static SoundBox getInstance() {
		if(instance == null)
			instance = new SoundBox();
		return instance;
	}
	
    public void play(String fileName) {
    	if(Main.configuration.soundEnabled)
    		new Player(((SoundFile)this.cache.get(fileName))).start();
    }
    
    public class Player extends Thread {

    	SoundFile file;
    	
    	public Player(SoundFile file) {
    		this.file = file;
    	}
    	
    	public void run() {
    		file.play();
    	}
    }
}
