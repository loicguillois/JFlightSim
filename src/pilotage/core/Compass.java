package pilotage.core;

import com.threed.jpct.SimpleVector;

/**
 * A compass wich can be displayed on the screen.
 *
 */
public class Compass {

	/**
	 * Some constants entirely arbitrary
	 */
	private static final SimpleVector NORTH = new SimpleVector(0,0,-1);
	private static final SimpleVector SOUTH = new SimpleVector(0,0,1);
	private static final SimpleVector EAST = new SimpleVector(1,0,0);
	private static final SimpleVector WEST = new SimpleVector(-1,0,0);
	
	private SimpleVector vector;
	
	public Compass(SimpleVector v) {
		this.vector = v;
	}
	
	public void test() {
		SimpleVector tmpVector = new SimpleVector(this.vector);
		tmpVector.makeEqualLength(new SimpleVector(1,0,0)); // get the direction vector
	}
	
}
