/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

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
        ParsedText intermediate = new ParsedText();
        
        
        // 2) Transform to MidiEvents
        
        //Track parsedTrack = sequence.createTrack();
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
            sequence = new Sequence(Sequence.PPQ, 10);
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
}
