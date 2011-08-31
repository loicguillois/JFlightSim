package pilotage.graphic;

import java.awt.Color;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

/**
 * jPCT doesn't provide a class for lights, we do that.
 * TODO another class Sun implementing a color/position relative to the date
 */
public class Light {
	private SimpleVector position;
	private Color color;
	
	public Light(int x, int y, int z, int r, int g, int b) {
		this.position = new SimpleVector(x,y,z);
		this.color = new Color(r,g,b);
	}

	public void addToWorld(World w) {
		w.addLight(this.position, this.color);
	}
}