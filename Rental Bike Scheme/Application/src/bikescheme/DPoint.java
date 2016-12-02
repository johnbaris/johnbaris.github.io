/**
 * 
 */
package bikescheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 *  
 * Docking Point for a Docking Station.
 * 
 * @author pbj
 *
 */
public class DPoint implements KeyInsertionObserver, BikeDockingObserver {
    public static final Logger logger = Logger.getLogger("bikescheme");

    private KeyReader keyReader; 
    private OKLight okLight;
    private RedLight redLight;
    private String instanceName;
    private int index;
    private BikeSensor bikeSensor;
    private BikeLock bikeLock;
    private FaultButton faultButton;
    private User user;
    private HubDisplay display;
    private DStation station;
    private boolean bool=false;
    
    /**
     * 
     * Construct a Docking Point object with a key reader and green ok light
     * interface devices.
     * 
     * @param instanceName a globally unique name
     * @param index of reference to this docking point  in owning DStation's
     *  list of its docking points.
     */
    
    public DPoint(DStation station, String instanceName, int index) {

     // Construct and make connections with interface devices
    	this.station = station;
        keyReader = new KeyReader(instanceName + ".kr");
        keyReader.setObserver(this);
        okLight = new OKLight(instanceName + ".ok");
        redLight = new RedLight(instanceName + ".rl");
        bikeSensor = new BikeSensor(instanceName + ".bs");
        bikeSensor.setObserver(this);
        bikeLock = new BikeLock(instanceName + ".bl");
        display = new HubDisplay(instanceName + ".hd");
        faultButton = new FaultButton(instanceName + ".fb");
        faultButton.setObserver(this);
        this.instanceName = instanceName;
        this.index = index;
        user = new User();
    }
   
    public void setDistributor(EventDistributor d) {
        keyReader.addDistributorLinks(d); 
        bikeSensor.addDistributorLinks(d); 
        faultButton.addDistributorLinks(d);
    }

    
    public void setCollector(EventCollector c) {
        okLight.setCollector(c);
        bikeLock.setCollector(c);
        redLight.setCollector(c);
        display.setCollector(c);
    }
    
    
    public String getInstanceName() {
        return instanceName;
    }
    public int getIndex() {
        return index;
    }
    
    /** 
     * Dummy implementation of docking point functionality on key insertion.
     * 
     * Here, just flash the OK light.
     */
 
    public void keyInserted(String keyId, String bikeId) {
        logger.fine(getInstanceName());
        
        station.hireABike(keyId, bikeId);
      
        okLight.flash();
        bikeLock.unlock();
             
    }
 

	public void bikeReturned(String bikeId) {

	}
    
	
	
	public void bikeDocked(String bikeId) {
        logger.fine(getInstanceName());   	
        station.dockABike(bikeId);
    	bikeLock.lock();
        okLight.flash();

	}


	@Override
	public void fbuttonPressed(String bikeId) {
		logger.fine(getInstanceName());
		bikeLock.lock();
		Date faultTime = Clock.getInstance().getDateAndTime();
		faultButton.waitFaultButton();
		Date dockTime = Clock.getInstance().getDateAndTime();
		if(Clock.minutesBetween(faultTime, dockTime)<=2){
		    redLight.flash();
		}
		 for(Bike b: Hub.bikeDetails){
	        	if(b.getBike().equals(bikeId)){
	        		b.setStatus("faulty");
	        	}
	     }
		 bool=true;
	}

	@Override
	public void removeBike(String staffKeyId, String bikeId) {
		logger.fine(getInstanceName());
		for(Bike b:Hub.bikeDetails){
			if(b.getStatus().equals("faulty")){
				station.removeABike(staffKeyId);
				okLight.flash();
				bikeLock.unlock();
			}
		}		
		
	}

	@Override
	public void findPoints(String keyId) {
		// TODO Auto-generated method stub
		
	}



	

 

}
