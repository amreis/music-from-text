/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import java.util.ArrayList;
import java.util.List;


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
        SongNote baseNote = new SongNote(Note.fromCharValue(note).getNoteIndex(), CURRENT_OCTAVE);
        if (rawText.isEmpty())
        {
            eventList.add(new SongEvent(baseNote, 
                    SongEventKind.NOTE, CURRENT_BPM));
            return;
        }
        char nextChar = rawText.charAt(0);
        CommandKind nextCharKind = classify(nextChar);
        if (nextCharKind != CommandKind.NOTE_MODIFIER)
            eventList.add(new SongEvent(baseNote,
                    SongEventKind.NOTE, CURRENT_BPM));
        else
        {
            rawText = removeFirstCharacter(rawText);
            if (isVowel(nextChar))
            {
                baseNote.toSharp();
            }
            else
            {
                baseNote.toBemol();
            }
            MidiNote newNote;
            
            eventList.add(new SongEvent(baseNote,
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

    private void treatOctaveModifier(char character) {
        final char NEWLINE = '\n';
        int value = -1;
        try {
            value = Integer.parseInt(String.valueOf(character));
        }
        catch (NumberFormatException e)
        {
            if (character == NEWLINE)
            {
                this.CURRENT_OCTAVE = MAIN_OCTAVE;
                return;
            }
        }
        
        if (isEven(value))
        {
            if (this.CURRENT_OCTAVE < 10) this.CURRENT_OCTAVE ++;
        }
        else if (this.CURRENT_OCTAVE > 0) this.CURRENT_OCTAVE --;
    }
    
    private void treatTriple() {
        SongEvent lastNote = null;
        int eventPosition = -1;
        int lastNotePosition;
        try {
        lastNotePosition  = findLastNotePosition(eventList);
        } catch (Exception e)
        { return; }
        
        eventPosition = lastNotePosition;
        lastNote = eventList.get(lastNotePosition);
        lastNote.setBPM(lastNote.getBPM() / 3);
        eventList.set(lastNotePosition, lastNote);
        
        
    }
    
    private int findLastNotePosition(ArrayList<SongEvent> eventList) 
            throws Exception
    {
        for (int i = eventList.size() - 1; i >= 0; i--)
            if (eventList.get(i).getEventKind() == SongEventKind.NOTE)
            {
                return i;
            }
        throw new Exception("Didn't find a note");
    }
    private List listAfter(List list, int index)
    {
        if (index + 1 <= list.size() - 1)
            return list.subList(index+1, list.size()-1);
        else return null;
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
