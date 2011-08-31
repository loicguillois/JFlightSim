package pilotage.util;

/**
 * This class allow to optimize file access.
 * -Notice that the name of a file is it's key. Then a file is loaded one time. 
 * -If you apply modifications to a file don't forget to reload it.
 *  
 */
public abstract class FileBuffer {

	/**
	 * The maximum number of <tt>File</tt> to be stored here.
	 * Must be <= 0 for unlimited buffer
	 */
	protected int sizeMax;

	/**
	 * This method is used to get a file.
	 * 
	 * @param fileName the file's name to get
	 * @return the file to be got
	 */
	public abstract Object get(String fileName);
	
	/**
	 * This method load a file and put it into the buffer
	 * 
	 * @param fileName file's name to be got
	 */
	public abstract void load(String fileName);
	
	/**
	 * This method load several files and put them into the buffer
	 * 
	 * @param fileName file's name to be got
	 */
	public abstract void load(String[] filesNames);
	
	
	/**
	 * This method reload a file
	 * 
	 * @param fileName 
	 */
	public final void reload(String fileName) {
		remove(fileName);
		load(fileName);
	}
	
	/**
	 * This method remove a file from the buffer
	 * 
	 * Warning : a bad use of this method will decrease performance
	 * then I forbid use outdoor of this class 
	 *  
	 * @param fileName file's name to be removed
	 */
	protected abstract void remove(String fileName);
	
	/**
	 * Check if a file is loaded in the buffer
	 * 
	 * @param fileName file's name to check
	 * @return true if loaded, false otherwise
	 */
	public abstract boolean isLoaded(String fileName);
	
	/**
	 * Check if the buffer is fillfulled
	 * 
	 * @return true if fillfulled, false otherwise
	 */
	public abstract boolean isFull();
	
	
	/**
	 * Check if the buffer have a limited size
	 * 
	 * @return true if sizeMax is usefull, false otherwise
	 */
	public final boolean isLimited() {
		return this.sizeMax <= 0;
	}

}
