/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;



public abstract class MidiNote
{
	private static final int NOTES_IN_AN_OCTAVE = 12;
	
	public static int midiValue(int noteIndex, int octave)
	{
		return noteIndex + octave * NOTES_IN_AN_OCTAVE;
	}
}
