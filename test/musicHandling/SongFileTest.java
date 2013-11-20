/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alister
 */
public class SongFileTest {
    
    public SongFileTest() {
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
     * Test of getFilename method, of class SongFile.
     */
    @Test
    public void testGetFilename() {
        System.out.println("getFilename");
        SongFile instance = new SongFile("dummy filename");
        String expResult = "dummy filename";
        String result = instance.getFilename();
        assertEquals(expResult, result);
        
        SongFile instance2 = new SongFile("");
        expResult = "";
        assertEquals(expResult, instance2.getFilename());
        
        SongFile instance3 = new SongFile(null);
        assertNull(instance3.getFilename());
    }

    /**
     * Test of setFilename method, of class SongFile.
     */
    @Test
    public void testSetFilename() {
        System.out.println("setFilename");
        String filename = "";
        SongFile instance = null;
        instance.setFilename(filename);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of readWholeFile method, of class SongFile.
     */
    @Test
    public void testReadWholeFile() {
        System.out.println("readWholeFile");
        SongFile instance = null;
        String expResult = "";
        String result = instance.readWholeFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
