/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;

public class SongTextParser {

    private String textToParse;
    private Track targetTrack;
    private int instrument = 0;

    public int getInstrument() {
        return instrument;
    }

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }

 
    public SongTextParser(String _textToParse, Track _targetTrack)
    {
        this.textToParse = _textToParse;
        this.targetTrack = _targetTrack;
    }
    
    public SongTextParser(String _textToParse, Track _targetTrack, int _instrument)
    {
        this(_textToParse, _targetTrack);
        this.instrument = _instrument;
    }
    
    
    
    public Track Parse() throws Exception
    {
        if (textToParse == null || "".equals(textToParse)) {
            throw new Exception("Cannot parse empty or null text");
        } 
        
        
        // Parsing is gonna occur in two steps:
        // 1) Generate the ArrayList<Note> that represents the song
        //ParsedText intermediate = new ParsedText();
        ParsedText intermediate = new ParsedText(textToParse);
        ArrayList<SongEvent> songEvents = intermediate.getEventList();
        
        // 2) Transform to MidiEvents
        try {
            targetTrack.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument ,0), 0));
        } catch (InvalidMidiDataException e)
        {
            Logger.getLogger(SongTextParser.class.getName()).log(Level.SEVERE, "Invalid Instrument");
        }
        translateToMidi(songEvents, targetTrack);
        /*
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(120.0f);
        sequencer.start();*/
        return targetTrack;
    }
   
    private void translateToMidi(ArrayList<SongEvent> events, Track resultingTrack) {
        
        int trackPosition = 4;
        for (SongEvent event : events)
        {
            switch (event.getEventKind())
            {
                case NOTE:
                    resultingTrack.add(addNoteOnToSong(event, trackPosition));
                    trackPosition = advanceInTrack(trackPosition, event.getBPM());
                    resultingTrack.add(addNoteOffToSong(event, trackPosition));
                    break;
                case PAUSE:
                    trackPosition = advanceInTrack(trackPosition, event.getBPM());
                    break;
            }
        }
    }

    private MidiEvent addNoteOnToSong(SongEvent event, int tick) {
        MidiEvent newEvent = null;
        try {
            SongNote noteToAdd = event.getNote();
            newEvent = new MidiEvent(
                    new ShortMessage(ShortMessage.NOTE_ON, noteToAdd.getMidiValue(), 100)
                    , tick);
            
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(SongTextParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newEvent;
    }

    private MidiEvent addNoteOffToSong(SongEvent event, int tick) {
        MidiEvent newEvent = null;
        try {
            SongNote noteToAdd = event.getNote();
            newEvent = new MidiEvent(
                    new ShortMessage(ShortMessage.NOTE_OFF, noteToAdd.getMidiValue(), 0)
                    , tick);
            
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(SongTextParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newEvent;
    }

    private int advanceInTrack(int trackPosition, float bpm) {
        return (int) (trackPosition + 480/bpm);
    }
    
    
}
