/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package musicHandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alister
 */
public class SongFile {
    
    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public SongFile(String filename)
    {
        this.filename = filename;
    }
    
    public String readWholeFile()
    {
        StringBuilder partialString = new StringBuilder();
        String auxiliary;
        assert(filename != null);
        Path filePath = Paths.get(filename);
        
        BufferedReader fileReader = null;
        try {
            fileReader = Files.newBufferedReader(filePath, Charset.defaultCharset());
            while ((auxiliary = fileReader.readLine()) !=  null)
            {
                partialString.append(auxiliary);
                partialString.append('\n');
            }
            fileReader.close();
        } catch (IOException ex) {
            Logger.getLogger(SongFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partialString.toString();
    }
}
