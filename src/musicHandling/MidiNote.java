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
    
    public MidiNote getSharp()
    {
        // TODO: CHANGE
        return DO;
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
    
   
}
