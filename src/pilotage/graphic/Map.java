package pilotage.graphic;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;

/**
 * This class store every informations about the "place" which simulate the World
 * TODO Load / save in XML file
 */
public class Map {

	/**
	 * General informations
	 */
	private String name;
	private String comment;
	
	/**
	 * Sky configuration
	 */
	private String skyFileName;
	private boolean skyIsPanoramic;
		
	/**
	 * Floor configuration
	 */
	private String floorTexture;
	private String floorModel;
	
	/**
	 * A default Map
	 * TODO will be destroyed when XML
	 */
	public Map() {
		this.skyFileName = "textures" + File.separatorChar	+ "sky.jpg";
		this.skyIsPanoramic = false;
		this.floorTexture = "textures" + File.separatorChar	+ "rocks.jpg";
		this.floorModel = "models" + File.separatorChar + "terascene.3ds";
	}
	
	public void load(WorldRenderer worldRenderer) {
		Texture rocks = new Texture(this.floorTexture);
		TextureManager.getInstance().addTexture("rocks", rocks);
		
		Object3D[] objs = Loader.load3DS(this.floorModel, 2000);
		if (objs.length > 0) {
			worldRenderer.terrain = objs[0];
			worldRenderer.terrain.setTexture("rocks");
		}
		
		worldRenderer.skyRenderer = new SkyRenderer(this.skyFileName, this.skyIsPanoramic);
		
	}
	
	public String toString() {
		return this.name + " : " + this.comment;
	}
	
}
