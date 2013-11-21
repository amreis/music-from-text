/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;

/**
 *
 * @author alister
 */
public class Music {
    private static Synthesizer synthesizer;
    private static Sequencer sequencer;
    private static Sequence sequence;
    private static Instrument[] instruments;
    private static MidiChannel[] midiChannels;
    
    private Track resultingMusic;
    
    public Music(String _text)
    {
        openSynth();
        synthesizer.loadInstrument(instruments[0]);
        resultingMusic = sequence.createTrack();
        SongTextParser parser = new SongTextParser(_text, resultingMusic);
        try {
            resultingMusic = parser.Parse();
        } catch (Exception ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            sequencer.setSequence(sequence);
            sequencer.setTempoInBPM(120.0f);
            sequencer.start();
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
}
