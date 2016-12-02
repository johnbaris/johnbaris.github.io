package bikescheme;

import java.util.ArrayList;
import java.util.List;

public class BankServer extends AbstractOutputDevice{

	
	 public BankServer(String instanceName) {
	        super(instanceName);   
	    }
	
	 
	 public void chargeCompleted() {
	        
	        String deviceClass = "BankServer";
	        String deviceInstance = getInstanceName();
	        String messageName = "paymentCompleted";
	        List<String> valueList = new ArrayList<String>();
	        
	        logger.fine(deviceInstance);
	        
	        super.sendEvent(
	            new Event(
	                Clock.getInstance().getDateAndTime(), 
	                deviceClass,
	                deviceInstance,
	                messageName,
	                valueList));
	        
	    }
}
