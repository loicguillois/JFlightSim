package pilotage;

import pilotage.configuration.Configuration;
import pilotage.core.Entity;
import pilotage.core.Montgolfier;
import pilotage.core.WorldManager;
import pilotage.graphic.WorldRenderer;
import pilotage.input.KeyboardManager;

/**
 * TODO
 * 
 * -> Serialize configuration
 * -> Swing
 * -> code package recording
 * -> complete WorldRenderer and Montgolfier
 * -> debug SkyRenderer
 * -> OSD
 */
public class Main {

	private static WorldRenderer displayEngine;
	private static WorldManager worldManager;
	
	private static boolean isPaused = false;
	
	public static Configuration configuration = new Configuration();
	private static Entity mainEntity;
	
	public static KeyboardManager keyboardManager;
	
	public static void main(String[] args) {
		runSimulation();
	}
	
	private static void runSimulation() {
		mainEntity = new Montgolfier();
		//mainEntity = (configuration.mainEntity.getClass()) configuration.mainEntity.getConstructors()[0].newInstance(new Object[0]);
		initializeWorldManager();
		initializeDisplay();
		new Thread(displayEngine).start();
		new Thread(worldManager).start();
	}
	
	private static void initializeWorldManager() {
		worldManager = new WorldManager(40); // 25 updates per seconds
		worldManager.addEntity(mainEntity);
	}
	
	private static void initializeDisplay() {
		displayEngine = new WorldRenderer(mainEntity);
	}
	
	public static Entity getMainEntity() {
		return mainEntity;
	}
	
	public static String getClock() {
		return worldManager.getClock();
	}
	
	public static void pause() {
		isPaused = true;
		worldManager.pause();
		displayEngine.pause();
	}
	
	public static void resume() {
		isPaused = false;
		worldManager.resume();
		displayEngine.resume();
	}
	
	public static boolean isPaused() {
		return isPaused;
	}
}
