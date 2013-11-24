/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;


public enum Note {


	DO(0),
        DO_SHARP(1),
	RE(2),
        RE_SHARP(3),
	MI(4),
	FA(5),
        FA_SHARP(6),
	SOL(7),
        SOL_SHARP(8),
	LA(9),
        LA_SHARP(10),
	SI(11);
	
	private int noteIndex;
	
	Note(int note)
	{
            this.noteIndex = note;
	}
	
	public int getNoteIndex()
	{
		return noteIndex;
	}
        
        public static Note fromCharValue(char value)
        {
            if (! Character.isLowerCase(value))
            {
                value = Character.toLowerCase(value);
            }
            switch(value)
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
        
        public static Note fromIndex(int index) 
        {
            switch(index)
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
            }
            return null;
        }

}
