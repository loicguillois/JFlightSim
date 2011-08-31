package pilotage.core;

import java.util.ArrayList;
import java.util.Iterator;


public class WorldManager implements Runnable {

	private ArrayList entities;
	private long granularity;
	private Clock clk;
	private boolean isPaused;
	
	public WorldManager(long granularity) {
		this.entities = new ArrayList();
		this.granularity = granularity;
		clk = new Clock(granularity, 8,0,0,0); // 8 a clock !
		this.isPaused = false;
	}
	
	public void run() {
		while(true) {
			Thread.yield();
			
			try {
				Thread.sleep(this.granularity);
			} catch(InterruptedException e) {
				System.err.println(e.toString());
			}
			if(!this.isPaused) {
				processEntities();
				this.clk.increment(1); // real time
			}
		}
	}
	
	/**
	 * Process key management and motion of every entities
	 *
	 */
	private void processEntities() {
		Entity tmp;
		Iterator it = entities.iterator();
		while(it.hasNext()) {
			tmp = (Entity) it.next();
			tmp.manageKeys();
			tmp.move();
		}
	}
	
	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}
	
	public String getClock() {
		return this.clk.toString();
	}
	
	public void pause() {
		this.isPaused = true;
	}
	
	public void resume() {
		this.isPaused = false;
	}
}
