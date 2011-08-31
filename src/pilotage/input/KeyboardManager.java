package pilotage.input;

import java.util.ArrayList;

import pilotage.Main;

import java.awt.*;
import java.awt.event.KeyEvent;

import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;

/**
 * He we manage every keys pressed. We stored them and we manage some general
 * feature like pausing, quit...
 *
 */
public class KeyboardManager {

	private ArrayList keysPressed = null;
	private KeyMapper keyMapper = null;
	
	public KeyboardManager(Frame frame) {
		this.keyMapper = new KeyMapper(frame);
		this.keysPressed = new ArrayList();
	}
	
	/**
	 * Use the KeyMapper to poll the keyboard
	 */
	public void poll() {
		KeyState state = null;

		do {
			state = this.keyMapper.poll();
			if (state != KeyState.NONE) {
				keyAffected(state);
			}
		} while (state != KeyState.NONE);
	}
	
	// TODO for every case
	private void keyAffected(KeyState state) {
		int code = state.getKeyCode();
		boolean event = state.getState();

		this.keysPressed = new ArrayList();
		
		switch (code) {
			case (KeyEvent.VK_ESCAPE): {
				if(event) {
					this.keysPressed.add("escape");
					manageQuit();
				}
				break;
			}
			case (KeyEvent.VK_Q): {
				if(event)
					this.keysPressed.add("q");
				break;
			}
			case (KeyEvent.VK_W): {
				if(event)
					this.keysPressed.add("w");
				break;
			}
			case (KeyEvent.VK_P): {
				if(event) {
					this.keysPressed.add("p");
					managePause();
				}
				break;
			}
		}
	}
	
	private void managePause() {
		if(!Main.isPaused())
			Main.pause();
		else
			Main.resume();
	}
	
	private void manageQuit() {
		System.exit(0);
	}
	
	public boolean isPressed(Object key) {
		return this.keysPressed.contains(key);
	}

	public KeyMapper getKeyMapper() {
		return keyMapper;
	}

	public void setKeyMapper(KeyMapper keyMapper) {
		this.keyMapper = keyMapper;
	}
}
