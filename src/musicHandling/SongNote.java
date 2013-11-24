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
            this.note = Note.SI;
            this.octave -- ;
        }
        else
        {
            this.note = Note.fromIndex(note.getNoteIndex() - 1);
        }
    }
}


/*
public class Note {
    private MidiNote baseNote;
    private int octave;
    public enum MidiNote {
        DO(60),
        DO_SHARP(61),
        RE(62),
        RE_SHARP(63),
        MI(64),
        FA(65),
        FA_SHARP(66),
        SOL(67),
        SOL_SHARP(68),
        LA(69),
        LA_SHARP(70),
        SI(71);

        private int midiValue;
        public static final int CENTRAL_OCTAVE = 5;
        public static final int NOTES_IN_AN_OCTAVE = 12;

        MidiNote(int value)
        {
            this.midiValue = value;
        }


        public int getMidiValue()
        {
            return this.midiValue;
        }


        public static MidiNote fromCharValue(char midiValue)
        {
            switch(midiValue)
            {
                case 'c':
                    return DO;
                case 'd':
                    return RE;
                case 'e':
                    return MI;
                case 'f':
                    return FA;
                case 'g':
                    return SOL;
                case 'a':
                    return LA;
                case 'b':
                    return SI;
            }
            return null;
        }

        public static MidiNote fromMidiValue(int midiValue) throws Exception
        {
            if (midiValue < 0)
                throw new Exception("Value is not valid for a note");
            switch (midiValue % NOTES_IN_AN_OCTAVE)
            {
                case 0:
                    return DO;
                case 1:
                    return DO_SHARP;
                case 2:
                    return RE;
                case 3:
                    return RE_SHARP;
                case 4:
                    return MI;
                case 5:
                    return FA;
                case 6:
                    return FA_SHARP;
                case 7:
                    return SOL;
                case 8:
                    return SOL_SHARP;
                case 9:
                    return LA;
                case 10:
                    return LA_SHARP;
                case 11:
                    return SI;
                default:
                    ; // Never Happens.
            }

            return null;
        }

        public int getMidivalueWithOctave(int octave)
        {
            int value = this.midiValue + (octave - CENTRAL_OCTAVE) * NOTES_IN_AN_OCTAVE;
            if (value < 0) value = 0;
            return value;
        }
    }

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
*/