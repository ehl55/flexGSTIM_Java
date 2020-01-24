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
 * SingleChannel tracks all settings for a single channel.
 * @author elee1
 */
public class SingleChannel extends Channel{
    
    private final int channelNum; //0-3, specific channel number
    private String stimCommand; //stimulation command corresponding to channelNum
    
    /** 
     * Constructor for a single channel.
     * @param a to set amplitude (mA)
     * @param d to set duration (uS)
     * @param f to set frequency (Hz)
     * @param o to set ON-WAVE duration (sec)
     * @param c to set channel number
     */
    public SingleChannel(double a, double d, double f, double o, int c) {
        this.channelNum = c; //important to do this first, so that updateSettings 
                             //can update stimulation command correctly 
        this.updateSettings(a,d,f,o);
    }
    
    @Override
    public void sendStimCommand(PrintWriter pw){        
        pw.print(stimCommand);
        pw.flush();
    }
    
    @Override
    public void updateSettingsTxtFile(){
        Path path = Paths.get("settings.txt");
        List<String> lines;
        
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            
            int position = (channelNum-3)*(-3); //the first 3 lines correspond to channel 3
                                         //the second 3 lines correspond to channel 2
                                         //etc.
            lines.set(position++, this.transAmp()); //set cathodic current
            lines.set(position++, this.transDur()); //set pulse width
            lines.set(position, "$STML(00,00)"); //set PRI, Sigenics reccomends leaving at 0
            
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(SingleChannel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void updateSettings(double a, double d, double f, double o) {
        this.setAmp(a);
        this.setDur(d);
        this.setFreq(f);
        this.setOnWave(o);
        
        /* Below, use math.pow(2,channelNum) because...
         * $STIM(00,01) => stimulate channel 0
         * $STIM(00,02) => stimulate channel 1
         * $STIM(00,04) => stimulate channel 2
         * etc.
         */
        this.stimCommand = "$STIM(00,0" + (int)Math.pow(2,this.channelNum) + ")";
    }
    
    /**
     * @return "Channel x" as a String
     */
    @Override
    public String toString() {
        return "Channel " + this.channelNum;
    }
}
