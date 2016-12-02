/**
 * 
 */
package bikescheme;

/**
 * 
 * Interface for any class with objects that are receiving keyReceived 
 * notifications from KeyReader objects.
 * 
 * @author pbj
 *
 */
public interface KeyInsertionObserver {
    public void keyInserted(String keyId, String bikeId);
	
	public void removeBike(String staffKeyId, String bikeId);

	public void findPoints(String keyId);

}
