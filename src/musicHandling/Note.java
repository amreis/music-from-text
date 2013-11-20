/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

/**
 *
 * @author alister
 */
public class Note {
    private MidiNote baseNote;
    private int octave;
    
    public Note(MidiNote _baseNote, int _octave)
    {
        this.baseNote = _baseNote;
        this.octave = _octave;
    }
    
    public Note(int _noteValue)
    {
        this.octave = (_noteValue / MidiNote.NOTES_IN_AN_OCTAVE) - MidiNote.CENTRAL_OCTAVE;
        this.baseNote = MidiNote.fromMidiValue(_noteValue);
    }
}
