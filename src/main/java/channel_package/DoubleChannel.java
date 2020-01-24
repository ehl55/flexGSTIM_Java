package channel_package;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DoubleChannel tracks all settings for a group of two channels. 
 * @author elee1
 */
public class DoubleChannel extends Channel{
    
    private final int ci; //first channel number in group of 2
    private final int cii; //second channel number in group of 2
    private String stimCommandi; //stimulation command corresponding to ci
    private String stimCommandii; //stimulation command corresponding to cii
    
    /**
     * Constructor for DoubleChannel
     * @param a to set amplitude (mA)
     * @param d to set duration (uS)
     * @param f to set frequency (Hz)
     * @param o to set ON-WAVE duration (sec)
     * @param ci to set first channel number in group
     * @param cii to set second channel number in group
     */
    public DoubleChannel(double a, double d, double f, double o, int ci, int cii) {
        this.ci = ci;
        this.cii = cii; 
        this.updateSettings(a, d, f, o);       
    }

     /**
     * Sends SINGLE STIM command to both channels, pseudo-simultaneously
     * @param pw referenced PrintWriter which allow us to send ASCII command to COMM port
     */
    @Override
    public void sendStimCommand(PrintWriter pw) {
        pw.print(stimCommandi);
        pw.flush();
        //send next command DIRECTLY after the first, very short delay 
        //makes stimulations appear simultaneous
        pw.print(stimCommandii);
        pw.flush();
    }

    @Override
    public void updateSettingsTxtFile() {
        Path path = Paths.get("settings.txt");
        List<String> lines;
        
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            
            int position = (ci-3)*(-3); //see updateSettingsTxtFile in SingleChannel class for explanation
            lines.set(position++, this.transAmp()); //set cathodic current
            lines.set(position++, this.transDur()); //set pulse width
            lines.set(position, "$STML(00,00)"); //set PRI, Sigenics reccomends leaving at 0
            
            //repeat for channel cii
            position = (cii-3)*(-3);
            lines.set(position++, this.transAmp()); 
            lines.set(position++, this.transDur());
            lines.set(position, "$STML(00,00)"); 
            
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(DoubleChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateSettings(double a, double d, double f, double o) {
        this.setAmp(a);
        this.setDur(d);
        this.setFreq(f);
        this.setOnWave(o);
        
        //see SingleChannel updateSettings() for explanation
        this.stimCommandi = "$STIM(00,0" + (int)Math.pow(2,this.ci) + ")";
        this.stimCommandii = "$STIM(00,0" + (int)Math.pow(2,this.cii) + ")";
    }
    
    /**
     * @return "Channel x & y" as a String
     */
    @Override
    public String toString() {
        return "Channel " + this.ci + " & " + this.cii;
    }  
}
