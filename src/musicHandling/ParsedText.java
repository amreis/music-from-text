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
    
    public ArrayList<SongEvent> getEventList() 
    {
        if (eventList == null)
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
        
    }
    
    
}
