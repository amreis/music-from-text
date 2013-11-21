/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import java.util.ArrayList;

/**
 *
 * @author alister
 */
public class ParsedText {
    private ArrayList<SongEvent> eventList;
    private String rawText;
    
    private final int MAIN_OCTAVE = 0;
    private final float MAIN_BPM = 120.0f;
    private float CURRENT_BPM = 120.0f;
    private int CURRENT_OCTAVE = 0;
    private final int lastExclamationIndex = 0;
    
    
    
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
        eventList = new ArrayList<SongEvent>();
        rawText = text;
    }

    private void generateEventList() 
    {
        rawText = rawText.toLowerCase();
        while (! rawText.isEmpty())
        {
            char classifyMe = rawText.charAt(0);
            rawText = removeFirstCharacter(rawText);
            CommandKind kind = classify(classifyMe);
            switch (kind)
            {
                case NOTE:
                    treatNote(classifyMe);
                    break;
            }
        }
    }

    private void treatNote(char note) {
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
    
    private String removeFirstCharacter(String value)
    {
        return value.substring(1);
    }
    
    private CommandKind classify(char command)
    {
        if (isAlphabetic(command))
        {
            if (isNoteChar(command))
                return CommandKind.NOTE;
            else            
                return CommandKind.NOTE_MODIFIER;
            
        }
        else if (isNumeric(command))        
            return CommandKind.OCTAVE_MODIFIER;
        
        else if (command == '?')
            return CommandKind.TRIPLE_LAST_NOTE;
        
        else if (command == ' ')        
            return CommandKind.PAUSE;
        
        else if (command == ';' || command == ',')        
            return CommandKind.BPM_MODIFIER;
        
        else return CommandKind.DO_NOTHING;
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
    
    private boolean isConsonant(char character)
    {
        character = Character.toLowerCase(character);
        return isAlphabetic(character) && ! isVowel(character);
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
    
    
}
