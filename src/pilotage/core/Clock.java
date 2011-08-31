package pilotage.core;

/**
 * Simulate a real clock. Used and synchronised by WorldManager.
 * @see WorldManager
 */
public class Clock implements Comparable {

	private long granularity;
	
	private int millisecondes;
	private int secondes;
	private int minutes;
	private int hours;
		
	public Clock(long g, int h, int m, int s, int ms) {
		this.granularity = g;
		
		this.millisecondes = ms;
		this.secondes = s;
		this.minutes = m;
		this.hours = h;
	}

	public void increment(long factor) {
		this.millisecondes += this.granularity * factor;
		if(millisecondes > 1000) {
			millisecondes -= 1000;
			secondes++;
		}
		if(secondes == 60) {
			secondes = 0;
			minutes++;
		}
		if(hours == 24) {
			hours = 0;
		}
	}
	
	public String toString() {
		return this.hours + ":" + this.minutes + ":" + this.secondes;
	}
	
	public int getAbsolute() {
		return this.hours * 60 * 60 + this.minutes * 60 + this.secondes + this.millisecondes / 1000;
	}
	
	public boolean equals(Clock clk) {
		return this.compareTo(clk) == 0;
	}
	
	/**
	 * Return >0 if greater than s in format "HH:MM:SS"
	 * TODO check !!!
	 */
	public int compareTo(String s) {
		return this.toString().compareTo(s);
	}
	
	/**
	 * Return >0 if greater than o
	 */
	public int compareTo(Object o) {
		return this.getAbsolute() - ((Clock)o).getAbsolute();
	}
	
}
