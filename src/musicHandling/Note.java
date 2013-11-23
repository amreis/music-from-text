/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;



public class Note {
    private MidiNote baseNote;
    private int octave;

    public MidiNote getBaseNote() {
        return baseNote;
    }

    public int getOctave() {
        return octave;
    }
    
    public Note(MidiNote _baseNote, int _octave)
    {
        this.baseNote = _baseNote;
        if (_octave < 0)
        {
            System.out.println("Octave value under zero, clamped at zero.");
            _octave = 0;
        }
        this.octave = _octave;
    }
    
    public Note(int _noteValue)
    {
        try {
        this.baseNote = MidiNote.fromMidiValue(_noteValue);
        }
        catch (Exception e){ 
            this.baseNote = null; this.octave = 0;
            
            return;
        };
        this.octave = distanceToCentralOctave(octaveFromMidiValue(_noteValue));
    }
    
    private int octaveFromMidiValue(int value)
    {
       
        return value / MidiNote.NOTES_IN_AN_OCTAVE;
    }
    
    private int distanceToCentralOctave(int _octave)
    {
        return _octave - MidiNote.CENTRAL_OCTAVE;
    }
}
