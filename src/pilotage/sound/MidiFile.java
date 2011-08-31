package pilotage.sound;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Sequence;

public class MidiFile extends File implements SoundFile {

	//final static long serialVersionUID = ???;
	
    private Sequencer player;
    private Sequence music;

    public MidiFile(String fileName) {
    	super(fileName);
    }

    public void play() {
    	try {
    		music = MidiSystem.getSequence(this);
    		player = MidiSystem.getSequencer();
    		player.open();
    		player.setSequence(music);
    		player.start();
    	} catch(Exception e) {
    		System.out.println(e.toString());
    	}
    }

    public boolean isFinished() {
    	return !player.isRunning();
    }
}