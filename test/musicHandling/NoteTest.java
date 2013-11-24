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
 * @author UserSeven
 */
public class NoteTest {
    
    public NoteTest() {
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
     * Test of fromCharValue method, of class Note.
     */
    @Test
    public void testFromCharValue() throws Exception {
        Note result = Note.fromCharValue('x');
        assertNull(result);
    }

    /**
     * Test of fromIndex method, of class Note.
     */
    @Test
    public void testFromNegativeIndex() throws Exception {
        Note result = Note.fromIndex(-1);
        assertNull(result);
    }
    @Test
    public void testFromBigIndex() throws Exception
    {
        Note result = Note.fromIndex(13);
        assertNull(result);
    }
    
    
}
