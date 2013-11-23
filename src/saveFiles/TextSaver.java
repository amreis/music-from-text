/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package saveFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class TextSaver {
    private String textToSave;
    public TextSaver(String saveMe)
    {
        textToSave = saveMe;
    }
    
    public void Save(String filename)
    {
        BufferedWriter writer = null;
        try
        {
            writer = new BufferedWriter(new FileWriter(filename));
            String[] splitText = getLines(textToSave);
            for (String line : splitText)
            {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
            
        }
        catch (IOException e)
        { }
        
    }
    
    private String[] getLines(String text)
    {
        return text.split("\n");
    }
}
