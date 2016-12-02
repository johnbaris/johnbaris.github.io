/**
 * 
 */
package bikescheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *  
 * Hub system.
 *
 * 
 * @author pbj
 *
 */
public class Hub implements AddDStationObserver, TimedNotificationObserver {
    public static final Logger logger = Logger.getLogger("bikescheme");

    private HubTerminal terminal;
    private HubDisplay display;
    private Map<String,DStation> dockingStationMap;
    public int number = 0;
    public static int totalJourneys = 0;
    public float journeyTime;
    public static ArrayList<User> userDetails = new ArrayList<User>();
    public static ArrayList<Bike> bikeDetails = new ArrayList<Bike>();
    private BankServer bankServer;
    private int totalFreeDPoints;
    
    /**
     * 
     * Construct a hub system with an operator terminal, a wall display 
     * and connections to a number of docking stations (initially 0). 
     * 
     * Schedule update of the hub wall display every 5 minutes with
     * docking station occupancy data.
     * 
     * @param instanceName
     */
    public Hub() {

    	Clock.createInstance();
        // Construct and make connections with interface devices
        terminal = new HubTerminal("ht");
        terminal.setObserver(this);
        display = new HubDisplay("hd");
        dockingStationMap = new HashMap<String,DStation>();
        bankServer = new BankServer("bb");

        
        // Schedule timed notification for generating updates of 
        // hub display. 

        // The idiom of an anonymous class is used here, to make it easy
        // for hub code to process multiple timed notification, if needed.
    
     Clock.getInstance().scheduleNotification(
                new TimedNotificationObserver() {

                    @Override
                    public void processTimedNotification() {
                        logger.fine("");

                        List<String> occupancyArray = new ArrayList<String>();
                        for(DStation d: getMap().values()){
                        	occupancyArray.add(d.getInstanceName());
                        	occupancyArray.add(String.valueOf(d.getEastPos()));
                        	occupancyArray.add(String.valueOf(d.getNorthPos()));
                        	if((d.getFreeDP()/d.getNumPoints())<= 0.15){
                        		d.setStatus("LOW");
                        	}
                        	else if((d.getFreeDP()/d.getNumPoints())>= 0.85){
                        		d.setStatus("HIGH");
                        	}
                        	occupancyArray.add(d.getStatus());
                        	occupancyArray.add(String.valueOf(d.getNumPoints() - d.getFreeDP()));
                        	occupancyArray.add(String.valueOf(d.getNumPoints()));
                        }
                   

                        List<String> occupancyData = occupancyArray;
                     
                        display.showOccupancy(occupancyData);
   
                    }

		

                },
                Clock.getStartDate(), 
                0, 
                5);
     

     Clock.getInstance().scheduleNotification(
             new TimedNotificationObserver() {
             	
             	
             	@Override
             	public void processTimedNotification() {

             		logger.fine("");
 		
             		for(User user : Hub.userDetails){
             			user.calcCharges();
             			user.setUsage(0);
             	
             		}
 		
             		bankServer.chargeCompleted();
             	}
             },

             Clock.parse("1 00:00"), 24, 0);
     
  
     }
    

     public Map<String,DStation> getMap(){
    	 return dockingStationMap;
     }
    
     public  void addUser(String name, String keyId, String card){
     	User newUser = new User();
     	newUser.setName(name);
     	newUser.setKey(keyId);
     	newUser.setCard(card);
     	userDetails.add(newUser);
     }
     
     public static void addBike(String bikeId){
      	Bike newBike = new Bike();
      	newBike.setBike(bikeId);
      	bikeDetails.add(newBike);
      }
 
     public ArrayList<String> getUser(){
         
    	ArrayList<String> user = new ArrayList<String>();
    	for(User u: userDetails){
    	   user.add(u.getName());
    	   user.add(u.getKey());
    	   user.add(String.valueOf(u.getUsage()));
    	}
      	return user;
      }
     
     public ArrayList<String> getBike(){
         
    	ArrayList<String> bike = new ArrayList<String>();
    	for(Bike b: bikeDetails){
    	   bike.add(b.getBike());
    	   bike.add(b.getStatus());
    	}
      	return bike;
      }


    public void setDistributor(EventDistributor d) {
        
        // The clock device is connected to the EventDistributor here, even
        // though the clock object is not constructed here, 
        // as no distributor is available to the Clock constructor.
        Clock.getInstance().addDistributorLinks(d);
        terminal.addDistributorLinks(d);
    }
    
    public void setCollector(EventCollector c) {
        display.setCollector(c); 
        terminal.setCollector(c);
        bankServer.setCollector(c);
    }
    

    /**
     * 
     */
    @Override
    public void addDStation(
            String instanceName, 
            int eastPos, 
            int northPos,
            int numPoints) {
        logger.fine("");
        
        DStation newDStation = 
                new DStation(this, instanceName, eastPos, northPos, numPoints);
        dockingStationMap.put(instanceName, newDStation);
        // Now connect up DStation to event distributor and collector.
        EventDistributor d = terminal.getDistributor();
        EventCollector c = display.getCollector();
        
        newDStation.setDistributor(d);
        newDStation.setCollector(c);
        
        newDStation.setFreeDP(numPoints);
        number++;
    }
    
    public DStation getDStation(String instanceName) {
        return dockingStationMap.get(instanceName);
    }

	@Override
	public void displayDStation() {
		logger.fine("");
		List<String> s = new ArrayList<String>();
		for(String key: dockingStationMap.keySet()){
			s.add(dockingStationMap.get(key).getInstanceName());
			s.add(String.valueOf(dockingStationMap.get(key).getEastPos()));
			s.add(String.valueOf(dockingStationMap.get(key).getNorthPos()));
			s.add(String.valueOf(dockingStationMap.get(key).getNumPoints()));
		}
		display.noDStations(s);
	}



	@Override
	public void viewStats() {
		logger.fine("");
		
		int usage = 0;
		for(User user: userDetails){
			usage += user.getUsage();
		}
		User.setTotalUsage(usage);
		
		for(DStation d:getMap().values()){
			totalFreeDPoints+= d.getFreeDP();
		}
		
		ArrayList<String> status = new ArrayList<String>();
		status.add(String.valueOf(totalJourneys));
		status.add(String.valueOf(totalFreeDPoints));
		status.add(String.valueOf(userDetails.size()));
		status.add(String.valueOf(User.getTotalUsage()/totalJourneys));
		display.showStatus(status);
	
	}






	@Override
	public void processTimedNotification() {
		
	}



}          



