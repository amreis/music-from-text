/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;


public class SongEvent {
    private float BPM;
    private Note note;
    private SongEventKind eventKind;

    public SongEventKind getEventKind() {
        return eventKind;
    }
    
    public SongEvent(Note _note, SongEventKind _eventKind, float _BPM)
    {
        this.BPM = _BPM;
        this.eventKind = _eventKind;
        this.note = _note;
        if (this.note == null)
            assert ( this.eventKind != SongEventKind.NOTE );
    }
    
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
    
    public float getBPM() {
        return BPM;
    }

    public void setBPM(float BPM) {
        this.BPM = BPM;
    }
    
}
