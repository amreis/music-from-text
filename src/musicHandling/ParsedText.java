/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alister
 */
public class ParsedText {
    private ArrayList<SongEvent> eventList;
    private String rawText;
    
    private final int MAIN_OCTAVE = 5;
    private final float MAIN_BPM = 120.0f;
    private final float HIGH_BPM = 480.0f;
    private final float LOW_BPM = 60.0f;
    private float CURRENT_BPM = 120.0f;
    private int CURRENT_OCTAVE = 5;
    private int lastExclamationIndex = 0;
    
    
    
    public ArrayList<SongEvent> getEventList() 
    {
        if (eventList == null || eventList.isEmpty())
        {
            generateEventList();
        }
        return eventList;
    }
    
    public ParsedText(String text) 
    {
        eventList = new ArrayList<>();
        rawText = text.toLowerCase();
    }

    private void generateEventList() 
    {
        while (! rawText.isEmpty())
        {
            char classifyMe = rawText.charAt(0);
            rawText = removeFirstCharacter(rawText);
            CommandKind kind = classify(classifyMe);
            switch (kind)
            {
               
                case BPM_MODIFIER:
                    treatBpmModifier(classifyMe);
                    break;
                case NO_OPERATION:
                    ;
                    break;
                case LOOP:
                    treatLoop();
                    break;
                case NOTE:
                    treatNote(classifyMe);
                    break;
                case NOTE_MODIFIER:
                    ; // Do Nothing, or maybe change the semitone again?
                    break;
                case OCTAVE_MODIFIER:
                    treatOctaveModifier(classifyMe);
                    break;
                case PAUSE:
                    treatPause();
                    break;
                case TRIPLE_LAST_NOTE:
                    treatTriple();
                    break;
            }
        }
    }

    private void treatNote(char note) {
        if (rawText.isEmpty())
        {
            eventList.add(new SongEvent(new Note(MidiNote.fromCharValue(note), CURRENT_OCTAVE), 
                    SongEventKind.NOTE, CURRENT_BPM));
            return;
        }
        char nextChar = rawText.charAt(0);
        CommandKind nextCharKind = classify(nextChar);
        if (nextCharKind != CommandKind.NOTE_MODIFIER)
            eventList.add(new SongEvent(new Note(MidiNote.fromCharValue(note), CURRENT_OCTAVE),
                    SongEventKind.NOTE, CURRENT_BPM));
        else
        {
            rawText = removeFirstCharacter(rawText);
            MidiNote baseNote = MidiNote.fromCharValue(note);
            int value = baseNote.getMidiValue();
            if (isVowel(nextChar))
            {
                value = getSharp(value);
            }
            else
            {
                value = getBemol(value);
            }
            MidiNote newNote;
            try {
                newNote = MidiNote.fromMidiValue(value);
            }
            catch (Exception e)
            {
                newNote = baseNote;
            }
            eventList.add(new SongEvent(new Note(newNote, CURRENT_OCTAVE),
                    SongEventKind.NOTE, CURRENT_BPM));
        }
    }
    private void treatLoop() {
        int eventsUntilNow = eventList.size();
        for (int i = lastExclamationIndex; i < eventsUntilNow; i++)
        {
            SongEvent event = eventList.get(i);
            if (event.getEventKind() != SongEventKind.BPM_CHANGE)
                eventList.add(eventList.get(i));
        }
        lastExclamationIndex = eventList.size();
    }

    private void treatOctaveModifier(char changeOctave) {
        final char NEWLINE = '\n';
        int value = -1;
        try {
            value = Integer.parseInt(String.valueOf(changeOctave));
        }
        catch (NumberFormatException e)
        {
            if (changeOctave == NEWLINE)
            {
                this.CURRENT_OCTAVE = MAIN_OCTAVE;
            }
        }
        
        if (isEven(value))
        {
            if (this.CURRENT_OCTAVE < 10) this.CURRENT_OCTAVE += 1;
        }
        else if (this.CURRENT_OCTAVE > 0) this.CURRENT_OCTAVE -= 1;
    }
    
    private void treatTriple() {
        SongEvent s = null;
        int eventPosition = -1;
        for (int i = eventList.size() - 1; i >= 0; i--)
            if (eventList.get(i).getEventKind() == SongEventKind.NOTE)
            {
                s = eventList.get(i);
                eventPosition = i;
                break;
            }
        if (s == null) return;
        else
        {
            List<SongEvent> afterEvent = eventList.subList(eventPosition+1, 
                    eventList.size() - 1);
            eventList.set(eventPosition + 1, s);
            eventList.set(eventPosition + 2, s);
            if (afterEvent.isEmpty()) return;
            eventList.addAll(eventPosition + 3, afterEvent);
        }
        
    }

    private void treatPause() {
        eventList.add(new SongEvent(null, SongEventKind.PAUSE, CURRENT_BPM));
    }

    private void treatBpmModifier(char classifyMe) {
        if (classifyMe == ';')
        {
            increaseBPM();
        }
        else
            decreaseBPM();
    }

    
    private String removeFirstCharacter(String value)
    {
        return value.substring(1);
    }
    
    private CommandKind classify(char command)
    {
        final char NEW_LINE = '\n';
        if (isAlphabetic(command))
        {
            if (isNoteChar(command))
                return CommandKind.NOTE;
            else            
                return CommandKind.NOTE_MODIFIER;
            
        }
        else if (isNumeric(command) || command == NEW_LINE)        
            return CommandKind.OCTAVE_MODIFIER;
        
        else if (command == '?' || command == '.')
            return CommandKind.TRIPLE_LAST_NOTE;
        
        else if (command == ' ')        
            return CommandKind.PAUSE;
        
        else if (command == ';' || command == ',')        
            return CommandKind.BPM_MODIFIER;
        
        else if (command == '!')
            return CommandKind.LOOP;
        else return CommandKind.NO_OPERATION;
    }

    private boolean isAlphabetic(char character) {
        character = Character.toLowerCase(character);
        return ((character >= 'a' && character <= 'z') ||
                (character >= 'A' && character <= 'Z'));
    }
    
    private boolean isNoteChar(char character)
    {
        character = Character.toLowerCase(character);
        return (character >= 'a' && character <= 'g');
    }
    
    private boolean isVowel(char character)
    {
        character = Character.toLowerCase(character);
        return (character == 'a' || character == 'e' ||
                character == 'i' || character == 'o' ||
                character == 'u');
    }

    private boolean isNumeric(char command) {
        return (command >= '0' && command <= '9');
    }

    private int getSharp(int value) {
        return value + 1;
    }

    private int getBemol(int value) {
        return value - 1;
    }

    
    
    private boolean isEven(int number)
    {
        return (number % 2) == 0;
    }

    
    private void increaseBPM() {
        if (this.CURRENT_BPM <= HIGH_BPM/2)
            this.CURRENT_BPM *= 2;
        
       
    }

    private void decreaseBPM() {
        if (this.CURRENT_BPM >= LOW_BPM*2)
            this.CURRENT_BPM /= 2;
    }
    
}
