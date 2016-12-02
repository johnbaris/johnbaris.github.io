package bikescheme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

public class EventTest {

	@Test
    public void testEqual() {
        assertEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,a,b, c, d, e, f") );
    }
    @Test
    public void testParameter1() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,z,b, c, d, e, f") );
    }
    
    @Test
    public void testParameter2() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,a,z, c, d, e, f") );
    }
    
    @Test
    public void testParameter3() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,a,b, z, d, e, f") );
    }
    
    @Test
    public void testParameter4() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,a,b, c, z, e, f") );
    }
    
    @Test
    public void testParameter5() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,a,b, c, d, z, f") );
    }
    
    @Test
    public void testParameter6() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,A,a,b, c, d, e, z") );
    }
    
    @Test
    public void testParameter7() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:00,Z,a,b, c, d, e, f") );
    }
    
    @Test
    public void testParameter8() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("1 00:01,A,a,b, c, d, e, f") );
    }
    
    @Test
    public void testParameter9() {
        assertNotEquals(
                new Event("1 00:00,A,a,b, c, d, e, f"),
                new Event("2 00:00,A,a,b, c, d, e, f") );
    }
    

     //This tests two lists of events with different sequences of same events. The dates are the same.
     
     
    @Test
    public void testListSameDay() {
        ArrayList<Event> events1 = new ArrayList<Event>();
        ArrayList<Event> events2 = new ArrayList<Event>();
        
        events1.add(new Event("1 00:00,A,a,b, c, d, e, f"));
        events1.add(new Event("1 00:00,B,z,y, x, v, w, u"));
        
        events2.add(new Event("1 00:00,B,z,y, x, v, w, u"));
        events2.add(new Event("1 00:00,A,a,b, c, d, e, f"));
        
        if (Event.listEqual(events1, events2) == false) {
            fail("Should have returned true.");
        }
    }

  //This tests two lists of events with different sequences of same events. The dates are not the same.
    
    @Test
    public void testListNotSameDay() {
        ArrayList<Event> events1 = new ArrayList<Event>();
        ArrayList<Event> events2 = new ArrayList<Event>();
        
        events1.add(new Event("1 00:00,A,a,b, c, d, e, f"));
        events1.add(new Event("2 10:00,B,z,y, x, v, w, u"));
        
        events2.add(new Event("2 10:00,B,z,y, x, v, w, u"));
        events2.add(new Event("1 00:00,A,a,b, c, d, e, f"));
        
        if (Event.listEqual(events1, events2) == true) {
            fail("Should have returned false.");
        }
    }
    
}
