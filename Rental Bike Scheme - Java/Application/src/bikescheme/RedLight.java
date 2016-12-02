package bikescheme;

import java.util.ArrayList;
import java.util.List;

public class RedLight extends AbstractOutputDevice {
	
	public RedLight(String instanceName) {
        super(instanceName);
    }
    
    public void flash() {
        logger.fine(getInstanceName());
        
        String deviceClass = "RedLight";
        String deviceInstance = getInstanceName();
        String messageName = "flashed";
        List<String> valueList = new ArrayList<String>();
 
        super.sendEvent(
            new Event(
                Clock.getInstance().getDateAndTime(), 
                deviceClass,
                deviceInstance,
                messageName,
                valueList));
        
        }
}
