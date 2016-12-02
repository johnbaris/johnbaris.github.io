/**
 * 
 */
package bikescheme;

// import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
// import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author pbj
 *
 */
public class SystemTest {
    private static final String LS = System.getProperty("line.separator");
    private static Logger logger;
    
    private EventDistributor distributor;
    private EventCollector collector;
    
    private List<Event> expectedOutputEvents;
    
    /*
     * 
     * INSERT SYSTEM TESTS HERE
     * 
     * 
     * 
     */
    
    
    /**
     * 
     * Setup demonstration system configuration:
     * 
     * Clock clk ----------------->
     * HubTerminal ht <-----------> Hub  -------->   HubDisplay d
     *                              |   
     *                              |   
     *                              |   
     *                              v
     * DSTouchScreen x.ts <---->  
     * CardReader x.cr <------->  DStation x   -------> KeyIssuer x.ki
     *                          |  x in {A,B}
     *                          |
     *                          |
     *                          v
     * KeyReader x.k.kr ---> DPoint x.k    ------> OKLight x.k.ok
     *                       for x.k in {A.1 ... A.5,
     *                                   B.1 ... B.3}
     *  
     *  This configuration is used in all the demonstration tests.
     *  
     *  It is inserted explicitly into each @Test block rather than the 
     *  @Before block so that alternate configurations can also be set up
     *  in this same test class.
     *   
     */
    public void setupDemoSystemConfig() {
        input("1 07:00, HubTerminal, ht, addDStation, A, 500, 350, 15");
        input("1 07:00, HubTerminal, ht, addDStation, B, 400, 300, 13");
    }
    
    public void setupBikes(){
    	input ("2 13:00, BikeSensor, B.2.bs, dockBike, bike4");
    	expect("2 13:00, BikeLock, B.2.bl, locked");
    	expect("2 13:00, OKLight,  B.2.ok, flashed");
    	input ("2 13:00, BikeSensor, A.4.bs, dockBike, bike5");
    	expect("2 13:00, BikeLock, A.4.bl, locked");
    	expect("2 13:00, OKLight,  A.4.ok, flashed");
    	input ("2 13:00, BikeSensor, A.7.bs, dockBike, bike6");
    	expect("2 13:00, BikeLock, A.7.bl, locked");
    	expect("2 13:00, OKLight,  A.7.ok, flashed");
    	input ("2 13:00, BikeSensor, B.5.bs, dockBike, bike7");
    	expect("2 13:00, BikeLock, B.5.bl, locked");
    	expect("2 13:00, OKLight,  B.5.ok, flashed");
    	input ("2 13:00, BikeSensor, A.2.bs, dockBike, bike8");
    	expect("2 13:00, BikeLock, A.2.bl, locked");
    	expect("2 13:00, OKLight,  A.2.ok, flashed");
    	input ("2 13:00, BikeSensor, B.2.bs, dockBike, bike9");
    	expect("2 13:00, BikeLock, B.2.bl, locked");
    	expect("2 13:00, OKLight,  B.2.ok, flashed");
    	input ("2 13:00, BikeSensor, B.1.bs, dockBike, bike10");
    	expect("2 13:00, BikeLock, B.1.bl, locked");
    	expect("2 13:00, OKLight,  B.1.ok, flashed");
    	
    }
    
    public void setupFaultyBikes(){
      	input("2 13:15, FaultButton, A.1.fb, pressFButton, bike2");
    	expect("2 13:15, BikeLock, A.1.bl, locked");
    	input("2 13:17, FaultButton, A.1.fb, pressingButton");
    	expect("2 13:17, RedLight, A.1.rl, flashed");
    }
    
    public void setupUsers(){

        input ("2 10:20, KeyReader, A.2.kr, insertKey, A.ki-1, bike3");
    	expect("2 10:20, OKLight,  A.2.ok, flashed");
    	expect("2 10:20, BikeLock, A.2.bl, unlocked");
    	input ("2 13:00, BikeSensor, B.8.bs, dockBike, bike3");
    	expect("2 13:00, BikeLock, B.8.bl, locked");
    	expect("2 13:00, OKLight,  B.8.ok, flashed");
    	input ("2 19:00, DSTouchScreen, A.ts, viewActivity");
    	expect ("2 19:00, DSTouchScreen, A.ts, viewPrompt, insertKey");
        input ("2 19:00, KeyReader, A.kt, keyInsertion, A.ki-1");
    	expect ("2 19:00, DSTouchScreen, A.ts, viewUserActivity, HireTime, HireDS, ReturnDS, Duration (min), 10:20:00, A, B, 160");
        
    }

    public void setUpSchedule(){
         setupBikes();
         expect("8 00:00, HubDisplay, hd, viewOccupancy, unordered-tuples, 6,"
              + "DSName, East, North, Status, #Occupied, #DPoints,"
              + " A,  500,   350,    LOW,         3,        15," 
              + " B,  400,   300,    LOW,         4,        13");
    }
    
    /**
     *  Run the "Register User" use case.
     * 
     */
    @Test
    public void registerUser() {
        logger.info("Starting test: registerUser");

        setupDemoSystemConfig();
        
        // Set up input and expected output.
        // Interleave input and expected output events so that sequence 
        // matches that when describing the use case main success scenario.
        logger.info("registerUser");
        
        input ("2 08:00, DSTouchScreen, A.ts, startReg, Alice");
        expect("2 08:00, CardReader, A.cr, enterCardAndPin");
        input ("2 08:01, CardReader, A.cr, checkCard, Alice-card-auth");
        expect("2 08:01, KeyIssuer, A.ki, keyIssued, A.ki-1");
        input ("2 08:05, DSTouchScreen, A.ts, startReg, John");
        expect("2 08:05, CardReader, A.cr, enterCardAndPin");
        input ("2 08:06, CardReader, A.cr, checkCard, John-card-auth");
        expect("2 08:06, KeyIssuer, A.ki, keyIssued,A.ki-2");
  
        
    }
    /**
     *  Run a show high/low occupancy test.
     *  
     *  Display event is scheduled to run only when minutes is multiple of 5,
     *  so only one of the input events should trigger the display. 
     * 
     */
    
    @Test
    public void addStation(){
    	logger.info("Starting test: addStation");
    	
    	setupDemoSystemConfig();
    	
    	input ("2 07:00, HubTerminal, ht, addDStation, C, 450, 280, 25");
    	input ("2 07:00, HubTerminal, ht, addDStation, D, 200, 400, 20");
    	input ("2 07:00, HubTerminal, ht, displayDStation");
    	expect ("2 07:00, HubDisplay, hd, numberOfDS, D, 200, 400, 20, A, 500, 350, 15, B, 400, 300, 13, C, 450, 280, 25");
    	setupBikes();
    }
    
    @Test 
    public void showHighLowOccupancy() {
        logger.info("Starting test: showHighLowOccupancy");
        
        setupDemoSystemConfig();
        setupBikes();
        input ("2 08:00, Clock, clk, tick");
        input ("2 08:01, Clock, clk, tick");
        input ("2 08:02, Clock, clk, tick");
        expect("2 08:00, HubDisplay, hd, viewOccupancy, unordered-tuples, 6,"
             + "DSName, East, North, Status, #Occupied, #DPoints,"
             + " A,  500,   350,    LOW,         3,        15," 
             + " B,  400,   300,    LOW,         4,        13");
    }
    
    /**
     * Run a test to demonstrate basic docking point interface
     * functionality.
     * 
     */   
    
    
    @Test
    public void hireBike(){
    	logger.info("Starting test: hireBike");
    	
    	setupDemoSystemConfig();
    	
    	input ("2 10:20, KeyReader, A.2.kr, insertKey, A.ki-2, bike2");
    	expect("2 10:20, OKLight,  A.2.ok, flashed");
    	expect("2 10:20, BikeLock, A.2.bl, unlocked");
    	
    }
    
    @Test
    public void dockBike(){
    	logger.info("Starting test: dockBike");
    	
    	setupDemoSystemConfig();
    	input ("2 13:00, BikeSensor, B.4.bs, dockBike, bike2");
    	expect("2 13:00, BikeLock, B.4.bl, locked");
    	expect("2 13:00, OKLight,  B.4.ok, flashed");
    }
    
    
    @Test
    public void viewUserActivity(){
    	logger.info("Starting test: viewUserActivity");
    	setupDemoSystemConfig();
    	input ("2 19:00, DSTouchScreen, A.ts, viewActivity");
    	expect ("2 19:00, DSTouchScreen, A.ts, viewPrompt, insertKey");
        input ("2 19:00, KeyReader, A.kt, keyInsertion, A.ki-2");
    	expect ("2 19:00, DSTouchScreen, A.ts, viewUserActivity, HireTime, HireDS, ReturnDS, Duration (min), 10:20:00, A, B, 160");
    }
    
    @Test
    public void reportFault(){
    	logger.info("Starting test: reportFault");
    	setupDemoSystemConfig(); 	
    	
    	input("2 13:15, FaultButton, A.1.fb, pressFButton, bike2");
    	expect("2 13:15, BikeLock, A.1.bl, locked");
    	input("2 13:17, FaultButton, A.1.fb, pressingButton");
    	expect("2 13:17, RedLight, A.1.rl, flashed");
    }
    
    @Test
    public void removeBike(){
        logger.info("Starting test: removeBike");
    	
    	setupDemoSystemConfig();
    	setupFaultyBikes();
    	input ("2 13:30, KeyReader, B.2.kr, insertStaffKey, staffkey, bike2");
    	expect("2 13:30, OKLight,  B.2.ok, flashed");
    	expect("2 13:30, BikeLock, B.2.bl, unlocked");
    	
    }
    
    
    @Test
    public void findFreePoints(){
    	
        logger.info("Starting test: findFreePoints");
    	
    	setupDemoSystemConfig();
    	setupBikes();
    	input("2 12:05, KeyReader, A.kt, insertKey1, A.ki-2");
    	expect("2 12:05, DSTouchScreen, A.ts, showLocations,B,400,300,13");
    }
    
    @Test
    public void viewTheStatus(){
    	
        logger.info("Starting test: viewTheStatus");
    	
    	setupDemoSystemConfig();
    	setupUsers();
    	input("5 16:10, HubTerminal, ht, viewStats");
    	expect("5 16:10, HubDisplay, hd, status, JourneyNumber, AvailableDP, noOfUsers, averageJourneyTime, 2, 28 ,2, 80");
    }
    
    @Test
    public void chargeUsers(){
    	logger.info("Starting test: chargeUsers");
    	setupDemoSystemConfig();
    	setUpSchedule();
        input ("8 00:00, Clock, clk, tick");
    	expect ("8 00:00, BankServer, bb, paymentCompleted");
    	
    }
    
    /*
     * 
     * SUPPORT CODE FOR RUNNING TESTS
     * 
     * NOTHING HERE SHOULD NEED TOUCHING
     * 
     * 
     */
     
    /**
     * Utility method for specifying an input event to drive in.
     * 
     * For use in test methods in this class.
     * 
     * @param inputEventString
     */
    private void input(String inputEventString) {
        distributor.enqueue(new Event(inputEventString));
    }
    
    /**
     * Utility method for specifying an expected output event.
     * 
     * For use in test methods in this class.
     * 
     * Relies on test object field expectedOutputEvents for passing
     * argument output event to checking method. 
     * 
     * @param outputEventString
     */
    private void expect(String outputEventString) {
        expectedOutputEvents.add(new Event(outputEventString));
    }
    
    
    /**
     * Queue up input events at event distributor.
     * 
     * Intended for calling from other classes, when input events are
     * read from a file, for example.
     * 
     * @param es input events
     */
    public void enqueueInputEvents(List<Event> es) {
        for (Event e : es) {
            distributor.enqueue(e);
        }
    }
    
    
    /**
     * Set expected output events.  These are compared with actual 
     * output events after a test is run.
     * 
     * Intended for calling from other classes, when input events are
     * read from a file, for example.
     *
     * @param es expected output events
     */
    public void setExpectedOutputEvents(List<Event> es) {
        expectedOutputEvents = es;
    }
    
    
    /**
     * Initialise logging framework so all log records FINER and above
     * are reported.
     * 
     */
    @BeforeClass
    public static void setupLogger() {
         
        // Enable log record filtering at FINER level.
        logger = Logger.getLogger("bikescheme"); 
        logger.setLevel(Level.FINER);
        
        Logger rootLogger = Logger.getLogger("");
        Handler handler = rootLogger.getHandlers()[0];
        handler.setLevel(Level.FINER);
    }
    
    /**
     * Setup test environment and starting system configuration.
     * 
     * Starting system configuration consists of a Hub object and
     * no Docking Station objects.
     * 
     * Suitable for calling directly as well as from JUnit.
     */
    @Before
    public void setupTestEnvAndSystem() {
       
        // Initialise core event framework objects
        
        distributor = new EventDistributor();
        collector = new EventCollector(); 
        
        // Create a hub object with interface devices.
        
              Hub hub = new Hub();
                
        // Connect up hub interface devices to event framework
                
        hub.setDistributor(distributor);
        hub.setCollector(collector);
         
        // Initialise expected output
        
        expectedOutputEvents = new ArrayList<Event>();
    }
    
   
     /**
     * Run test and check results. 
     * 
     * Run this after input events have been loaded into event queue in 
     * event distributor and expected output events have been loaded into
     * expectedOutputEvents field of object this.
     * 
     * If called directly, not via JUnit runner, the AssertionError, thrown
     * when some assertion fails, should be caught.
     */ 
    @After
    public void runAndCheck() {
        List<Event> actualOutputEvents = runTestAndReturnResults();
        checkTestResults(expectedOutputEvents, actualOutputEvents);
    } 
    
    
    /**
     * Inject input events in distributor queue into system and return the
     * resulting output events.
     * 
     * This method can called directly as an alternative to runAndCheck
     * if results want to be seen, but not checked.
     * 
     * @return Output events from test run
     */
    public List<Event> runTestAndReturnResults() {

        distributor.sendEvents();
        List<Event> actualOutputEvents = collector.fetchEvents();
        return actualOutputEvents;
    }
    
    /**
     * Compare expected and actual output events.  
     * 
     * Uses Event.listEqual() to do the comparison.  This not the same as
     * the normal list equality. 
     * 
     * @see Event
     * 
     * @param expectedEvents
     * @param actualEvents
     */
    public void checkTestResults(
            List<Event> expectedEvents,  // Avoid field name expectedOutputEvents
            List<Event> actualEvents) {
            
        // Log output event sequences for easy comparison when different.

        
        StringBuilder sb = new StringBuilder();
        sb.append(LS);
        sb.append("Expected output events:");
        sb.append(LS);
        for (Event e : expectedEvents) {
            sb.append(e);
            sb.append(LS);
        }
        sb.append("Actual output events:");
        sb.append(LS);
        for (Event e : actualEvents) {
            sb.append(e);
            sb.append(LS);
        }
        logger.info(sb.toString());
        
        assertTrue("Expected and actual output events differ",
                Event.listEqual(expectedEvents, actualEvents));
               
    }
}
