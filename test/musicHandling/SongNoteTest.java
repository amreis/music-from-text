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
public class SongNoteTest {
    
    public SongNoteTest() {
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
     * Test of toSharp method, of class SongNote.
     */
    @Test
    public void testToSharp() {
        SongNote testNote = new SongNote(Note.SI.getNoteIndex(), 10);
        int oldMidiValue = testNote.getMidiValue();
        testNote.toSharp();
        // The octave is too high, the note should not change.
        assertEquals(oldMidiValue, testNote.getMidiValue());
        
        // Test octave change:
         testNote = new SongNote(Note.SI.getNoteIndex(), 5);
        int oldOctave = testNote.getOctave();
        testNote.toSharp();
        assertEquals(oldOctave, testNote.getOctave() - 1);
    }

    /**
     * Test of toBemol method, of class SongNote.
     */
    @Test
    public void testToBemol() {
        SongNote testNote = new SongNote(Note.DO.getNoteIndex(), 0);
        int oldMidiValue = testNote.getMidiValue();
        testNote.toBemol();
        // The octave is too low, the note should not change.
        assertEquals(oldMidiValue, testNote.getMidiValue());
        
        testNote = new SongNote(Note.DO.getNoteIndex(), 5);
        int oldOctave = testNote.getOctave();
        testNote.toBemol();
        
        assertEquals(oldOctave, testNote.getOctave() + 1);
    }
    
}
