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
public enum CommandKind {
    NOTE,
    NOTE_MODIFIER,
    OCTAVE_MODIFIER,
    BPM_MODIFIER,
    NO_OPERATION,
    TRIPLE_LAST_NOTE,
    LOOP,
    PAUSE;
}
