package pilotage.configuration;

import pilotage.core.Montgolfier;

/**
 * Here we store every parameters which can be modified by user.
 * TODO save and load to/from XML file
 *
 */
public class Configuration {

	/**
	 * Display
	 */
	public boolean fullscreen;
	public boolean openGL;
	public int width, height; // in pixel
	
	/**
	 * For jPCT rendering
	 */
	public int maxPolysVisible;
	public int farPlane;
	public int glColorDepth; // in bits
	
	/**
	 * sound
	 */
	public boolean soundEnabled;
	public int cacheSize; // a file quantity
	
	/**
	 * Rules
	 */
	public Class mainEntity; // the player drive a Montgolier, a Flight, a Planner ?
	public boolean considerGazQuantity;
	public int automaticBalloonCount;
	
	/**
	 * Creating a default configuration
	 *
	 */
	public Configuration() {
		this.fullscreen = false;
		this.openGL  = false;
		this.width = 800;
		this.height = 600;
		
		this.maxPolysVisible = 25000;
		this.farPlane = 4000;
		this.glColorDepth = 32;
		
		this.mainEntity = Montgolfier.class;
		this.considerGazQuantity = false;
		this.automaticBalloonCount = 0;
		
		this.soundEnabled = false;
		this.cacheSize = 8;
	}
}
