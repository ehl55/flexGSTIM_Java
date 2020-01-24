package channel_package;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides helper methods for reading txt files and sending commands. Also provides
 * a wait() function for creating delays.
 * @author elee1
 */
public class TxtUtil {
    
    /**
     * Reads line by line from filename and sends to COMM port via referenced PrintWriter
     * @param filename is name of txt file
     * @param pw is referenced PrintWriter
     */
    public static void sendAllCommands(String filename, PrintWriter pw) {
        BufferedReader reader=null;
        boolean fileFound = true;
        
            try {
                File f = new File(filename);
                reader = new BufferedReader(new FileReader(f));
                String str = null;
                while((str = reader.readLine()) != null){
                    pw.print(str);
                    pw.flush();
                    wait(30); //small wait time, give some slack to the device            
                }
            } catch (FileNotFoundException e) {
                System.out.println("File Not Found");
                fileFound = false;
            } catch (IOException e) {
                System.out.println("IOException");
            } catch (Exception e) {
                System.out.println("Some Other Exception");
            } finally{
                if(fileFound)
                    try {
                        reader.close();
                } catch (IOException ex) {
                    System.out.println("IOException");
                    Logger.getLogger(TxtUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }
    
    /**
     * Delay for a specified period of time
     * @param w is wait time in milliseconds
     */
    public static void wait(int w) {
        try{
           Thread.sleep(w);
        }
        catch(InterruptedException ex)
        {
           Logger.getLogger(TxtUtil.class.getName()).log(Level.SEVERE, null, ex);
           Thread.currentThread().interrupt();
        }       
    }
}
