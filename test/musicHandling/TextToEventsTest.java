/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author UserSeven
 */
public class TextToEventsTest {
    
    public TextToEventsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEventList method, of class TextToEvents.
     */
    @Test
    public void testGetEventList() {
        
        TextToEvents test1 = new TextToEvents("");
        assertTrue(test1.getEventList().isEmpty());
        
        TextToEvents test2 = new TextToEvents("/()~.\\@$");
        assertTrue(test2.getEventList().isEmpty());
        // Try to triple last note when there's none
        TextToEvents test3 = new  TextToEvents("?");
        assertTrue(test3.getEventList().isEmpty());
        
        // Try to create a loop when there's nothing to loop.
        assertTrue(new TextToEvents("!").getEventList().isEmpty());
        
        // Assert that both capitalizations work 
        TextToEvents test5 = new TextToEvents("A");
        assertTrue(test5.getEventList().get(0).getNote().getBaseNote() == Note.LA);
        test5 = new TextToEvents("a");
        assertTrue(test5.getEventList().get(0).getNote().getBaseNote() == Note.LA);
        
        // Assert that only changing the BPM does not create events.
        TextToEvents test6 = new TextToEvents(";");
        assertTrue(test6.getEventList().isEmpty());
        test6 = new TextToEvents(",");
        assertTrue(test6.getEventList().isEmpty());
        
        // Assert that BPM is correctly going down and up.
        TextToEvents test7 = new TextToEvents(",a");
        assertTrue(test7.getEventList().get(0).getBPM() == 60.0f);
        
        test7 = new TextToEvents(";a");
        assertTrue(test7.getEventList().get(0).getBPM() == 240.0f);
        
        // Assert that BPM saturates at max 480.0f
        TextToEvents test8 = new TextToEvents(";;;;;;;;;;;;a");
        assertTrue(test8.getEventList().get(0).getBPM() == 480.0f);
        
        // Assert that BPM saturates at min 60.0f
        TextToEvents test9 = new TextToEvents(",,,,,,,,,,,,a");
        assertTrue(test9.getEventList().get(0).getBPM() == 60.0f);
    }
    
}
