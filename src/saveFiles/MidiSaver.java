/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saveFiles;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiSystem;
/**
 *
 * @author UserSeven
 */
import javax.sound.midi.Sequence;
public class MidiSaver {
    
    private Sequence sequenceToSave;
    public MidiSaver(Sequence sequenceToSave)
    {
        this.sequenceToSave = sequenceToSave;
    }
    
    public void Save(String filename)
    {
        try {
            MidiSystem.write(sequenceToSave, MidiSystem.getMidiFileTypes()[0], new File(filename));
        } catch (IOException ex) {
            Logger.getLogger(MidiSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
