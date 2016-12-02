package bikescheme;

import java.util.List;

public class FaultButton extends AbstractInputDevice
{
	 private BikeDockingObserver observer;
	
	 public FaultButton(String instanceName) {
	        super(instanceName);   
	    }
	
	 public void setObserver(BikeDockingObserver o) {
	        observer = o;
	    }
	 
	  @Override
	    public void receiveEvent(Event e) {
	        
	        if (e.getMessageName().equals("pressFButton") 
	                && e.getMessageArgs().size() == 1) {
	            
	            String bikeId = e.getMessageArg(0);
	            pressFButton(bikeId);
	            
	        } else {
	            super.receiveEvent(e);
	        }
	    }
	  
	  public void pressFButton(String bikeId) {

	        logger.fine(getInstanceName());
	        
	        observer.fbuttonPressed(bikeId);
	    }
	  
	    public void waitFaultButton() {
	        String deviceClass = "FaultButton";
	        String deviceInstance = getInstanceName();
	        String messageName = "pressingButton";
	        
	        logger.fine(deviceInstance);
	        
	        List<String> messageArgs = 
	                getDistributor().fetchMatchingEvent(
	                        deviceClass, 
	                        deviceInstance, 
	                        messageName);
	    }
}
