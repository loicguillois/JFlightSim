package pilotage.util;

import java.util.HashMap;
import java.io.File;

/**
 * A simple implementation of the abstract class <tt>FileBuffer</tt>.
 * It used a <tt>HashMap</tt> as container of datum
 * 
 *
 */
public class SimpleFileBuffer extends FileBuffer {

	protected HashMap datum;
	
	public SimpleFileBuffer(int sizeMax) {
		this.sizeMax = sizeMax;
		if(isLimited())
			datum = new HashMap(sizeMax);
		else
			datum = new HashMap();
	}
	
	public Object get(String fileName) {
		if(!isLoaded(fileName))
			load(fileName);
		return datum.get(fileName);
	}
	
	public synchronized void load(String fileName) {
		if(isFull())
			removeOlder();
		datum.put(fileName, new File(fileName));
	}
	
	public boolean isLoaded(String fileName) {
		return this.datum.containsKey(fileName);
	}
	
	
	public boolean isFull() {
		if(isLimited())
			return this.datum.size() == this.sizeMax;
		else
			return false;
	}
	
	public void removeOlder() {
		if(!this.datum.isEmpty())
			this.datum.remove(this.datum.keySet().iterator().next());
	}

	public void load(String[] filesNames) {
		for(int i=0; i<filesNames.length; i++)
			load(filesNames[i]);
	}

	public void remove(String fileName) {
		datum.remove(fileName);
	}
}
