/**
 * 
 */
package bikescheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.lang.Math;

/**
 *  
 * Docking Station.
 * 
 * @author pbj
 *
 */
public class DStation implements StartRegObserver, ViewActivityObserver, KeyInsertionObserver, BikeDockingObserver {
    public static final Logger logger = Logger.getLogger("bikescheme");

    private String instanceName;
    private String status;
    private int eastPos;
    private int northPos;
    private int numPoints;
    private Hub hub;
    private KeyReader keyReader;
    private DSTouchScreen touchScreen;
    private CardReader cardReader; 
    private KeyIssuer keyIssuer;
    private static List<DPoint> dockingPoints;
    private int freeDPoints;
    private HubDisplay display;
    private User user;
    private String keyId;
    
    /**
     * 
     * Construct a Docking Station object with touch screen, card reader
     * and key issuer interface devices and a connection to a number of
     * docking points.
     * 
     * If the instance name is <foo>, then the Docking Points are named
     * <foo>.1 ... <foo>.<numPoints> . 
     * 
     * @param instanceName
     */
    public DStation(Hub hub, String instanceName, int eastPos,int northPos,int numPoints) {
        
     // Construct and make connections with interface devices
        
    	this.hub = hub;
        this.instanceName = instanceName;
        this.eastPos = eastPos;
        this.northPos = northPos;
        this.numPoints = numPoints;
        
        keyReader = new KeyReader(instanceName + ".kt");
        keyReader.setObserver(this);
        
        touchScreen = new DSTouchScreen(instanceName + ".ts");
        touchScreen.setObserver(this);
        touchScreen.setViewActivityObserver(this);
        cardReader = new CardReader(instanceName + ".cr");
        display = new HubDisplay(instanceName + ".hd");
        keyIssuer = new KeyIssuer(instanceName + ".ki");
        
        user = new User();
        dockingPoints = new ArrayList<DPoint>();
        
        for (int i = 1; i <= numPoints; i++) {
            DPoint dp = new DPoint(this ,instanceName + "." + i, i - 1);
            dockingPoints.add(dp);
        }
    }
    
    public void hireABike(String keyId, String bikeId){
    	char c = getInstanceName().charAt(0);
		String s = "";
		s = s + c;
		for(String v: hub.getMap().keySet()){
			if(s.equals(hub.getMap().get(v).getInstanceName())){
				for(DStation d: hub.getMap().values()){
					if(d.getInstanceName().equals(s)){
						d.freeDPoints++;
					}
				}
			}
		}
		
        Hub.totalJourneys++;
		
		String key = keyId;
		for(User user : Hub.userDetails){
			String key1 = user.getKey();
			if(key1.equals(key)){
				user.setBike(bikeId);
				user.setHireStation(s);
				user.setHireDate(Clock.getInstance().getDateAndTime());
			}
		}
    }
    
    public void dockABike(String bikeId){
    	String bike = bikeId;
    	for(Bike b: Hub.bikeDetails){
        	if(b.getBike().equals(bike)){
        		b.setStatus("docked");
        	}
        }
        char c = getInstanceName().charAt(0);
		String s = "";
		s = s + c;
		for(String v: hub.getMap().keySet()){
			if(s.equals(hub.getMap().get(v).getInstanceName())){
				for(DStation d: hub.getMap().values()){
					if(d.getInstanceName().equals(s)){
						d.freeDPoints--;
					}
				}
			}
		}
		for(User user : Hub.userDetails){
			user.setBike(bikeId);
			String bike1 = user.getBike();
			if(bike1.equals(bike)){
				user.setReturnStation(s);
				user.setReturnDate(Clock.getInstance().getDateAndTime());
			}
			else{
				hub.addBike(bike);
			}
		}
    }
    
    public void removeABike(String staffKeyId){
    	char c = getInstanceName().charAt(0);
		String s = "";
		s = s + c;
		for(String v: hub.getMap().keySet()){
			if(s.equals(hub.getMap().get(v).getInstanceName())){
				for(DStation d: hub.getMap().values()){
					if(d.getInstanceName().equals(s)){
						d.freeDPoints++;
					}
				}
			}
		}
    }
    
    public void setFreeDP(int dp){
    	this.freeDPoints = dp;
    }
    
    public int getFreeDP(){
    	return freeDPoints;
    }
    
    public void setStatus(String status){
    	this.status = status;
    }
    
       
    void setDistributor(EventDistributor d) {
        touchScreen.addDistributorLinks(d); 
        cardReader.addDistributorLinks(d);
        keyReader.addDistributorLinks(d);
        for (DPoint dp : dockingPoints) {
            dp.setDistributor(d);
        }
    }
    
    void setCollector(EventCollector c) {
        touchScreen.setCollector(c);
        cardReader.setCollector(c);
        keyIssuer.setCollector(c);
        display.setCollector(c);
        for (DPoint dp : dockingPoints) {
            dp.setCollector(c);
        }
    }
    
    /** 
     * Dummy implementation of docking station functionality for 
     * "register user" use case.
     * 
     * Method called on docking station receiving a "start registration"
     * triggering input event at the touch screen.
     * 
     * @param personalInfo
     */
    public void startRegReceived(String personalInfo) {
        logger.fine("Starting on instance " + getInstanceName());
        User user = new User();
        cardReader.requestCard();  // Generate output event
        logger.fine("At position 1 on instance " + getInstanceName());
        
        user.setCard(cardReader.checkCard());    // Pull in non-triggering input event
        logger.fine("At position 2 on instance " + getInstanceName());
        
        String key = keyIssuer.issueKey(); // Generate output event
        
        user.setName(personalInfo);
        user.setKey(key);
        hub.addUser(user.getName(), user.getKey(), user.getCard());
    }
    
    public String getInstanceName() {
        return instanceName;
    }
    public String getStatus() {
        return status;
    }
   
    public int getEastPos() {
        return eastPos;
    }
    
    public int getNorthPos() {
        return northPos;
    }
    
    public int getNumPoints() {
        return numPoints;
    }

    
	@Override
	public void viewActivityReceived() {
		logger.fine(getInstanceName());
		touchScreen.showPrompt("insertKey");
		String key = keyReader.waitForKeyInsertion();
		List <String> userActivity = new ArrayList <String>();
		for(User user : Hub.userDetails){
			String key1 = user.getKey();
			if(key1.equals(key)){
					user.setUsage(user.calcUsage());
					userActivity.addAll(Arrays.asList(String.valueOf(user.getHireDate()).substring(11, 19)));
					userActivity.addAll(Arrays.asList(user.getHireStation()));
					userActivity.addAll(Arrays.asList(user.getReturnStation()));
					userActivity.addAll(Arrays.asList(String.valueOf(user.getUsage())));
					List<String> s = new ArrayList<String>();
					s.add(user.getKey());
					s.add(key);
			}
			//user.calcCharges();
		}
		touchScreen.showUserActivity(userActivity);
	}


	
	@Override
	public void keyInserted(String keyId, String bikeId) {

	}
	

	@Override
	public void removeBike(String staffKeyId, String bikeId) {
		
	}
	
	@Override
	public void findPoints(String keyId){
		
		logger.fine(getInstanceName());
		
		keyReader.setKey(keyId);
		String key = keyReader.getKey();
	
		char c = getInstanceName().charAt(0);
		String s = "";
		s = s + c;
		int east = 0;
		int north = 0;
		if(freeDPoints >0){
			for(String v: hub.getMap().keySet()){
				if(s.equals(hub.getMap().get(v).getInstanceName())){
					
					east = hub.getMap().get(v).getEastPos();
					north = hub.getMap().get(v).getNorthPos();
				}
			}
			for(String v: hub.getMap().keySet()){
				if(((Math.abs(east - hub.getMap().get(v).getEastPos())) < 200 )&& (Math.abs(north - hub.getMap().get(v).getNorthPos()) < 200)){
					if(!hub.getMap().get(v).getInstanceName().equals(s)){
					      touchScreen.findDPoints(hub.getMap().get(v).getInstanceName(), hub.getMap().get(v).getEastPos(), hub.getMap().get(v).getNorthPos(), hub.getMap().get(v).getNumPoints());
					}
				}
			}
			for(User user : Hub.userDetails){
				String key1 = user.getKey();
				if(key1.equals(key)){
					user.setUsage(user.getUsage() - 15);
				}
			}
		}
	    
	}

	@Override
	public void bikeDocked(String bikeId) {
		
	}

	@Override
	public void fbuttonPressed(String bikeId) {
		
	}

}
