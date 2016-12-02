/*
 * Copyright: pbj
 * 
 */
package bikescheme;

import java.util.List;

/**
 * Input device for reading contactless keys
 * 
 * @author pbj
 *
 */
public class KeyReader extends AbstractInputDevice {


    private KeyInsertionObserver observer;
    private String keyId;
    /**
     * @param instanceName  
     */
    public KeyReader(String instanceName) {
        super(instanceName);   
    }
    
    /*
     * 
     *  METHODS FOR HANDLING TRIGGERING INPUT
     *  
     */
    
    /**
     * @param o
     */
    public void setObserver(KeyInsertionObserver o) {
        observer = o;
    }
    
    
    /** 
     *    Select device action based on input event message
     *    
     *    @param e
     */
    @Override
    public void receiveEvent(Event e) {
        
        if (e.getMessageName().equals("insertKey")    // User hires bike
                && e.getMessageArgs().size() == 2) {
            
            String keyId = e.getMessageArg(0);
            String bikeId = e.getMessageArg(1);
            insertKey(keyId, bikeId);
            
        } 
        else if(e.getMessageName().equals("insertStaffKey")  // We created another message for when the user
                && e.getMessageArgs().size() == 2) {     // remove a bike 
            
            String staffKeyId = e.getMessageArg(0);  
            String bikeId = e.getMessageArg(1);  
            insertKey3(staffKeyId, bikeId);      
            
        }
        else if(e.getMessageName().equals("insertKey1")  // We created another message for when the user
                && e.getMessageArgs().size() == 1) {     // wants to view their usage
            
            String keyId = e.getMessageArg(0);  
            insertKey4(keyId);  
        }
        else {
            super.receiveEvent(e);
        }
    }
    
    
    /**
     * Model insert key operation on a key reader object
     * 
     * @param keyId
     */
    
    public void setKey(String keyID){
    	keyId = keyID;
    }
    
    public String getKey(){
    	return keyId;
    }
    
    public void insertKey(String keyId, String bikeId) {    // Insert key and OK light flashes
        logger.fine(getInstanceName());
        
        observer.keyInserted(keyId, bikeId); 
    }
    
    public void insertKey3(String staffKeyId, String bikeId) {   // Insert key, OK light flashes and bike unlocks (hire bike)
        logger.fine(getInstanceName());
        
        observer.removeBike(staffKeyId, bikeId);
    }
    
    public void insertKey4(String keyId) {   // Insert key, OK light flashes and bike unlocks (hire bike)
        logger.fine(getInstanceName());
        
        observer.findPoints(keyId);
    }
    

    /*
     * 
     *  METHOD FOR HANDLING NON-TRIGGERING INPUT
     *  
     */
    
    /**
     * Fetch a non-triggering input event indicating that a key has
     * been inserted into the key reader.
     * 
     * @return the key Id.
     */
    public String waitForKeyInsertion() {
        String deviceClass = "KeyReader";
        String deviceInstance = getInstanceName();
        String messageName = "keyInsertion";
        
        logger.fine(deviceInstance);
        
        List<String> messageArgs = 
                getDistributor().fetchMatchingEvent(
                        deviceClass, 
                        deviceInstance, 
                        messageName);
        return messageArgs.get(0);
    }
  


}
