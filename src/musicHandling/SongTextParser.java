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

/**
 *
 * @author alister
 */
public class SongTextParser {
    private static Synthesizer synthesizer;
    private static Sequencer sequencer;
    private static Sequence sequence;
    private static Instrument[] instruments;
    private static MidiChannel[] midiChannels;
    
    private String textToParse;
    private boolean synthOpen;

    public boolean isSynthOpen() {
        return synthOpen;
    }

 
    public SongTextParser(String _textToParse)
    {
        this.textToParse = _textToParse;
        openSynth();
        if (synthesizer != null) this.synthOpen = true;
    }
    
    
    
    public Track Parse() throws Exception
    {
        if (textToParse == null || textToParse == "")        
            throw new Exception("Cannot parse empty or null text");
        else if (!synthOpen)
            throw new Exception("Error opening synthesizer.");
        
        // Parsing is gonna occur in two steps:
        // 1) Generate the ArrayList<Note> that represents the song
        //ParsedText intermediate = new ParsedText();
        ParsedText intermediate = new ParsedText(textToParse);
        ArrayList<SongEvent> songEvents = intermediate.getEventList();
        
        // 2) Transform to MidiEvents
        Track parsedTrack = sequence.createTrack();
        synthesizer.loadInstrument(instruments[0]);
        parsedTrack.add(new MidiEvent(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 0, 0 ,0), 0));
        translateToMidi(songEvents, parsedTrack);
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(120.0f);
        sequencer.start();
        return null;
    }
    
    
    private void openSynth()
    {
        try
        {
            if (synthesizer == null) 
            {
                if ((synthesizer = MidiSystem.getSynthesizer()) == null)
                {
                    System.out.println("getSinthesizer() failed!");
                    return;
                }
            }
            synthesizer.open();
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            if (! (sequencer instanceof Synthesizer))
            {
                System.out.println("Linking ...");
                Receiver synthReceiver = synthesizer.getReceiver();
                Transmitter seqTransmitter = sequencer.getTransmitter();
                seqTransmitter.setReceiver(synthReceiver);
            }
            else 
                synthesizer = (Synthesizer)sequencer;
            sequence = new Sequence(Sequence.PPQ, 4);
        }
        catch (Exception ex) {  }
        
        Soundbank sb = synthesizer.getDefaultSoundbank();
        
        if (sb != null)
        {
            instruments = sb.getInstruments();
            synthesizer.loadInstrument(instruments[0]);
        }
        
        midiChannels = synthesizer.getChannels();
        
        
        
    }
    
    private void closeSynth()
    {
        if (synthesizer != null)
            synthesizer.close();
        
        if (sequencer != null)
            sequencer.close();
        
        sequencer = null;
        synthesizer = null;
        instruments = null;
    }

    private void translateToMidi(ArrayList<SongEvent> events, Track resultingTrack) {
        
        int trackPosition = 0;
        int tick;
        for (SongEvent event : events)
        {
            switch (event.getEventKind())
            {
                case BPM_CHANGE:
                    break;
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
            Note noteToAdd = event.getNote();
            MidiNote midiInfo = noteToAdd.getBaseNote();
            newEvent = new MidiEvent(
                    new ShortMessage(ShortMessage.NOTE_ON, midiInfo.getMidivalueWithOctave(noteToAdd.getOctave()), 100)
                    , tick);
            
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(SongTextParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newEvent;
    }

    private MidiEvent addNoteOffToSong(SongEvent event, int tick) {
        MidiEvent newEvent = null;
        try {
            Note noteToAdd = event.getNote();
            MidiNote midiInfo = noteToAdd.getBaseNote();
            newEvent = new MidiEvent(
                    new ShortMessage(ShortMessage.NOTE_OFF, midiInfo.getMidivalueWithOctave(noteToAdd.getOctave()), 0)
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
