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
public enum Notes {
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
    
    Notes(int value)
    {
        this.midiValue = value;
    }
    
    
    public int getMidiValue()
    {
        return this.midiValue;
    }
    
    public Notes getSharp()
    {
        // TODO: CHANGE
        return DO;
    }
    
    public static Notes fromMidiValue(int midiValue) throws Exception
    {
        if (midiValue >= 60 && midiValue <= 71)
        {
            switch (midiValue)
            {
                case 60:
                    return DO;
                case 61:
                    return DO_SHARP;
                case 62:
                    return RE;
                case 63:
                    return RE_SHARP;
                case 64:
                    return MI;
                case 65:
                    return FA;
                case 66:
                    return FA_SHARP;
                case 67:
                    return SOL;
                case 68:
                    return SOL_SHARP;
                case 69:
                    return LA;
                case 70:
                    return LA_SHARP;
                case 71:
                    return SI;
                default:
                    ; // Never Happens.
            }
        }
        else
        {
           throw new Exception("MidiValue exceeds central octave values");
        }
        return null;
    }
    
   
}
