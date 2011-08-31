package pilotage.graphic;

import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.image.BufferStrategy;

import pilotage.Main;
import pilotage.core.Entity;
import pilotage.input.KeyboardManager;

import com.threed.jpct.Camera;
import com.threed.jpct.Config;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.Lights;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.OcTree;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

/**
 * TODO fullscreen en OPENGL, touches en OpenGL
 * The display engine
 */
public class WorldRenderer implements Runnable {
	
	/**
	 * Some jPCT related stuff
	 */
	private FrameBuffer buffer = null;
	protected World theWorld = null;
	private Camera camera = null;
		
	/**
	 * Some AWT related stuff
	 */
	private Frame frame = null;
	private Graphics gFrame = null;
	private BufferStrategy bufferStrategy = null;
	private GraphicsDevice device = null;
	private int titleBarHeight = 0;
	private int leftBorderWidth = 0;
	
	/**
	 * Some things for the "game logic"
	 */
	protected Object3D terrain = null;
	private Light sun;
	private Entity entity = null;
	private boolean isPaused = false;
	
	/**
	 * Some good features.
	 */
	protected SkyRenderer skyRenderer;
	private OSD osd;
	
	/**
	 * The constructor. Here we are initializing things...
	 */
	public WorldRenderer(Entity entity) {
		
		this.osd = OSD.getInstance();
		
		this.entity = entity;
		
		/**
		 * Some very important parameters for quality/performance
		 */
		Config.maxPolysVisible = Main.configuration.maxPolysVisible;
		Config.farPlane = Main.configuration.farPlane;
		Config.glColorDepth = Main.configuration.glColorDepth;
		Config.tuneForOutdoor();

		/**
		 * Initialize the World instance and get the TextureManager (a singleton)
		 */
		theWorld = new World();

		/**
		 * Setup the lighting
		 * TODO apply hours modification
		 */
		Config.fadeoutLight = false;
		theWorld.getLights().setOverbrightLighting(Lights.OVERBRIGHT_LIGHTING_DISABLED);
		theWorld.getLights().setRGBScale(Lights.RGB_SCALE_2X);
		theWorld.setAmbientLight(25, 30, 30);
		sun = new Light(1000, -2000, -1000, 18, 15, 12);
		sun.addToWorld(this.theWorld);
		
		Map map = new Map();
		map.load(this);
		
		terrain.enableLazyTransformations();
		theWorld.addObject(terrain);

		/**
		 * We add the entity in the 3d world
		 */
		entity.addToWorld(theWorld);
		entity.model.translate(0, -250, 0);

		/**
		 * The game entities are building themselves, so we only have to build
		 * the terrain here
		 */
		terrain.build();

		/**
		 * The terrain isn't located where we want it to, so we take
		 * care of this here:
		 */
		SimpleVector pos = terrain.getCenter();
		pos.scalarMul(-1f);
		terrain.translate(pos);
		terrain.rotateX((float) -Math.PI / 2f);
		terrain.translateMesh();
		terrain.rotateMesh();
		terrain.setTranslationMatrix(new Matrix());
		terrain.setRotationMatrix(new Matrix());

		/**
		 * Tries to rebuild the object in a way that it can be rendered
		 *  by using triangle strips in the most optimal way. 
		 */
		terrain.createTriangleStrips(2);

		/**
		 * Setup the octree and the collision mode/listener for the terrain
		 */
		OcTree oc = new OcTree(terrain, 50, OcTree.MODE_OPTIMIZED);
		terrain.setOcTree(oc);
		oc.setCollisionUse(OcTree.COLLISION_USE);

		Config.collideOffset = 400;

		terrain.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		terrain.setCollisionOptimization(Object3D.COLLISION_DETECTION_OPTIMIZED);

		/**
		 * Get the camera
		 */
		camera = theWorld.getCamera();
		
        initializeFrame();
	}
	
	/**
	 * This initializes the AWT frame
	 */
	private void initializeFrame() {
		if (Main.configuration.fullscreen) {
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			device = env.getDefaultScreenDevice();
			GraphicsConfiguration gc = device.getDefaultConfiguration();
			frame = new Frame(gc);
			frame.setUndecorated(true);
			frame.setIgnoreRepaint(true);
			device.setFullScreenWindow(frame);
			if (device.isDisplayChangeSupported()) {
				device.setDisplayMode(new DisplayMode(Main.configuration.width, Main.configuration.height, 32, 0));
			}
			frame.createBufferStrategy(2);
			bufferStrategy = frame.getBufferStrategy();
			Graphics g = bufferStrategy.getDrawGraphics();
			bufferStrategy.show();
			g.dispose();
		} else {
			frame = new Frame();
			frame.setTitle("Pilotage - v0.01");
			frame.pack();
			Insets insets = frame.getInsets();
			titleBarHeight = insets.top;
			leftBorderWidth = insets.left;
			frame.setSize(Main.configuration.width + leftBorderWidth + insets.right, Main.configuration.height + titleBarHeight + insets.bottom);
			frame.setResizable(false);
			frame.show();
			gFrame = frame.getGraphics();
		}

		/**
		 * The listeners are bound to the AWT frame...
		 */
		this.entity.setKeyboardManager(new KeyboardManager(frame));
	}
	
	/**
	 * update the display
	 */
	private void display() {
	      if (!Main.configuration.openGL) {
	          if (!Main.configuration.fullscreen) {
	             buffer.display(gFrame, leftBorderWidth, titleBarHeight);
	          } else {
	             Graphics g=bufferStrategy.getDrawGraphics();
	             g.drawImage(buffer.getOutputBuffer(), 0, 0, null);
	             bufferStrategy.show();
	             g.dispose();
	          }
	       } else {
	          buffer.displayGLOnly();
	       }
	}
	
	/**
	 * Move the camera. The camera will always look at the entity
	 * TODO better
	 */
	private void moveCamera() {
		SimpleVector entityCenter = entity.getObject3D().getTransformedCenter();
		
		SimpleVector cameraPosition = new SimpleVector(entityCenter);
		cameraPosition.add(new SimpleVector(-100,-50, 0));
		camera.setPosition(cameraPosition);
		
		camera.lookAt(entityCenter);
	}
	
	/**
	 * This is the game's main loop.
	 */
	public void run() {
		World.setDefaultThread(Thread.currentThread());

		buffer = new FrameBuffer(Main.configuration.width, Main.configuration.height, FrameBuffer.SAMPLINGMODE_NORMAL);
		buffer.enableRenderer(IRenderer.RENDERER_SOFTWARE);
		buffer.setBoundingBoxMode(FrameBuffer.BOUNDINGBOX_NOT_USED);

		if(Main.configuration.openGL)
			switchOptions();
		
		buffer.optimizeBufferAccess();

		while (true) {
			
			// adapte the camera
			moveCamera();
			
			// update keys pressed
			entity.pollKbManager();
				
			// TODO Collision detection
			/*if(terrain.checkForCollision(SimpleVector.ORIGIN, 0) != 0) {
				System.out.println("Crash");
			 }*/
			
			// update the display
			
			buffer.clear();
			this.skyRenderer.blit(buffer);
			theWorld.renderScene(buffer);
			theWorld.draw(buffer);
			this.osd.blit(buffer);
			if(this.isPaused)
				buffer.getGraphics().drawString("PAUSE", Main.configuration.width / 2, Main.configuration.height / 2);
			buffer.update();
			display();

			// allow other Threads to execute
			Thread.yield();
		}
	}
	
   /**
	 * This is for switching settings. Currently, only switching from OpenGL
	 * to software and back is supported here. This is done to avoid
	 * switching modes while polling the keyboard, because it may have
	 * undesired side-effects otherwise.
	 */
	private void switchOptions() {
		KeyMapper keyMapper = Main.getMainEntity().getKeyboardManager().getKeyMapper();
		if (buffer.usesRenderer(IRenderer.RENDERER_OPENGL)) {
			keyMapper.destroy();
			buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
			buffer.enableRenderer(IRenderer.RENDERER_SOFTWARE, IRenderer.MODE_OPENGL);
			Main.configuration.openGL = false;
			if (Main.configuration.fullscreen) {
				device.setFullScreenWindow(null);
			}
			frame.hide();
			frame.dispose();
			initializeFrame();
		} else {
			frame.hide();
			buffer.enableRenderer(IRenderer.RENDERER_OPENGL, IRenderer.MODE_OPENGL);
			buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
			Main.configuration.openGL = true;
			keyMapper.destroy();
			Main.getMainEntity().getKeyboardManager().setKeyMapper(new KeyMapper());
		}
	}
	
	public void pause() {
		this.isPaused = true;
	}
	
	public void resume() {
		this.isPaused= false;
	}
	
}
