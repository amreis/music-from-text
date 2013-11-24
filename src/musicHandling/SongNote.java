/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

public class SongNote
{
    private Note note;
    private int octave;

    public Note getBaseNote() {
        return note;
    }

    public int getOctave() {
        return octave;
    }
	
    public SongNote(int note, int octave)
    {
        this.note = Note.fromIndex(note);
        this.octave = octave;
    }
    public int getMidiValue()
    {
        return MidiNote.midiValue(note.getNoteIndex(), octave);
    }
    
    public void toSharp()
    {
        if (this.note == Note.SI)
        {
            if (this.octave == 10) return;
            this.note = Note.DO;
            this.octave ++;
        }
        else
        {
            this.note = Note.fromIndex(note.getNoteIndex() + 1);
        }
    }
    
    public void toBemol()
    {
        
        if (this.note == Note.DO)
        {
            if (this.octave == 0) return;
            this.note = Note.SI;
            this.octave -- ;
        }
        else
        {
            this.note = Note.fromIndex(note.getNoteIndex() - 1);
        }
    }
}

