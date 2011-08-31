package pilotage.core;

import pilotage.input.KeyboardManager;

import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;


/* ****** IMPORTANT : METTRE A CHAQUE FOIS A JOUR COORDONNEE DE L'OBJET
 * 			ET COORDONNEES DU MODELE 3D, ici ce n'est pas encore le cas*************
 */

/**
 * It's an object wich evolve in a simulated world. Moreover it represent
 * the player. Here keyboard is managed.
 */
public abstract class Entity {

	protected KeyboardManager keyboardManager;
	
	/**
	 * params for 3D
	 */
	public Object3D model; // public for performance reason
	protected SimpleVector speed = new SimpleVector();
	
	/**
	 * params for physic
	 */
	// put here

	
	// OK
	public void addToWorld(World world) {
		world.addObject(model);
	}

	// OK
	public void move() {

		SimpleVector a;
		
		a = this.model.getYAxis();
		a.scalarMul(speed.y);
		this.model.translate(a);

		a = this.model.getZAxis();
		a.scalarMul(speed.x);
		this.model.translate(a);

		a = this.model.getXAxis();
		a.scalarMul(speed.z);
		this.model.translate(a);
	}

	// TODO : meters
	public SimpleVector getSpeed() {
		return speed;
	}

	// TODO : meters
	public void setSpeed(SimpleVector s) {
		this.speed.set(s);
	}
	
	// OK
	public Object3D getObject3D() {
		return this.model;
	}
	
	// OK
	public abstract void manageKeys();

	public void setKeyboardManager(KeyboardManager keyboardManager) {
		this.keyboardManager = keyboardManager;
	}
	
	public void pollKbManager() {
		this.keyboardManager.poll();
	}

	public KeyboardManager getKeyboardManager() {
		return keyboardManager;
	}
}
