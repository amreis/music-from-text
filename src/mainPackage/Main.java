/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainPackage;
import musicHandling.ParsedText;
/**
 *
 * @author alister
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MainFrame().setVisible(true);
        ParsedText p = new ParsedText("as432512jpsdijofoa");
        p.getEventList();
    }
    
}
