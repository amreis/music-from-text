/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

public enum CommandKind {
    NOTE,
    NOTE_MODIFIER,
    OCTAVE_UP,
    OCTAVE_DOWN,
    OCTAVE_RESET,
    BPM_UP,
    BPM_DOWN,
    NO_OPERATION,
    TRIPLE_LAST_NOTE,
    LOOP,
    PAUSE;
}
