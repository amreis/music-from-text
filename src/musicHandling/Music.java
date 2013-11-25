/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;


public class Music {
    private static Synthesizer synthesizer;
    private static Sequencer sequencer;
    private static Sequence sequence;
 
    private Track resultingMusic;
    private boolean paused;


    public boolean isPaused() {
        return paused;
    }
    
    public Music(String _text, int _instrument)
    {
        openSynth();
        //synthesizer.loadInstrument(instruments[0]);
        resultingMusic = sequence.createTrack();
        EventsToSound parser = new EventsToSound(_text, resultingMusic, _instrument);
        try {
            resultingMusic = parser.Parse();
        } catch (Exception ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public Sequence getSequence()
    {
        return sequence;        
    }
    
    private void openSynth()
    {
        try
        {
            if (synthesizer == null) 
            {
                if ((synthesizer = MidiSystem.getSynthesizer()) == null)
                {
                    System.out.println("Failed to get a valid Synthesizer");
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
        catch (InvalidMidiDataException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, "Invalid sequence specification");
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, "Midi not available!");
        }
        
        Soundbank sb = synthesizer.getDefaultSoundbank();
        
      
        
        
        
    }
    
    private void closeSynth()
    {
        if (synthesizer != null)
            synthesizer.close();
        
        if (sequencer != null)
            sequencer.close();
        
        sequencer = null;
        synthesizer = null;
    }
    
    public void play()
    {
       
        try {
            sequencer.setSequence(sequence);
            if (! this.paused)
                sequencer.setTickPosition(0);
            sequencer.setTempoInBPM(120.0f);
            sequencer.start();
            this.paused = false;
        } catch (InvalidMidiDataException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void stop()
    {
        sequencer.stop();
        sequencer.setTickPosition(0);
    }
    
    public void pause()
    {
        if (this.isPaused()) return;
        sequencer.stop();
        this.paused = true;
    }
}
