package channel_package;

import java.io.PrintWriter;

/**
 * Channel class tracks all information for single (or group) of channels.
 * The subclasses of Channel:
 * (1) singleChannel := used to send stimulation/setup ASCII commands to SINGLE channel
 * (2) doubleChannel := used to send stimulation/setup ASCII commands to DUAL channels
 * @author elee1
 */
public abstract class Channel {
    private double amp; //unit of milli-amps
    private double dur; //unit of micro-seconds
    private double freq; //unit of Hz
    private double onWave; //unit of sec
    
    /**
     * Sends out a SINGLE stimulation command using referenced PrintWriter pw
     * @param pw 
     */
    public abstract void sendStimCommand(PrintWriter pw);
    
     /**
     * Updates amplitude, duration, freq, on-wave duration, AND corresponding STIM command
     * of the Channel object ONLY. Does NOT update settings.txt
     * @param a to set amplitude (mA)
     * @param d to set duration (uS)
     * @param f to set frequency (Hz)
     * @param o to set ON-WAVE duration (sec)
     */
    public abstract void updateSettings(double a, double d, double f, double o);
    
    /**
     * Updates relevant lines in settings.txt. Assumes lines 0-2 correspond to Channel 3,
     * lines 3-5 correspond to Channel 2, lines 6-8 correspond to Channel 1, lines 9-11 correspond
     * to Channel 0. See Sigenics API for details. 
     */
    public abstract void updateSettingsTxtFile();
    
    /**
     * Translates amplitude setting into ASCII command.
     * @return ASCII command as a String
     */
    public String transAmp() {
        int a = (int)(this.amp*1000); //convert to microAmps
        a = a/64; //step size of 64microAmps
        
        String hexRep = Integer.toHexString(a).toUpperCase();
        
        if(hexRep.length()<2) return "$STML(00,0" + hexRep + ")";
        else return "$STML(00," + hexRep + ")";
    }
    
    /**
     * Translates pulse duration/width setting into ASCII command
     * @return ASCII command as a String
     */
    public String transDur() {
        int d = (int)(this.getDur()/1.66); //step size of 1.66 microSec
        String hexRep = Integer.toHexString(d).toUpperCase();
        
        if(hexRep.length()<2) return "$STML(00,0" + hexRep + ")";
        else return "$STML(00," + hexRep + ")";
    }
    
    /*********Getters*********/
    
    /**@return amplitude in milliAmps (mA)*/
    public double getAmp() {
        return this.amp;
    }
    
    /**@return duration in micro-seconds (uS)*/
    public int getDur() {
        return (int)this.dur;
    }
    
    /**@return frequency in Hz*/
    public int getFreq() {
        return (int)this.freq;
    }
    
    /**@return ON-WAVE duration in seconds*/
    public int getOnWave() {
        return (int)this.onWave;
    }
    
    /*********Setters*********/
    
    /**
     * Set amplitude in milliAmps (mA).
     * Range is [0mA,8.128mA] which corresponds to [0uA,8128uA].
     * If out of range, amp will be set to min/max bound. 
     * @param x 
     */
    protected void setAmp(double x) {
        if(x>8.128) this.amp = 8.128;
        else if (x<0) this.amp = 0;
        else this.amp = x;
    }
    
    /**
     * Set duration in micro-seconds (uS).
     * Range is [0uS,423uS].
     * If out range, dur will be set to min/max bound. 
     * @param x 
     */
    protected void setDur(double x) {
        if(x>423) this.dur = 423;
        else if (x<0) this.dur = 0;
        else this.dur = x;
    }
    
    /**
     * Set frequency in Hz.
     * Range is [0Hz,100Hz].
     * If out of range, freq will be set to min/max bound.
     * @param x 
     */
    protected void setFreq(double x) {
        if(x>100) this.freq = 100;
        else if (x<0) this.freq = 0;
        else this.freq = x;
    }
    
    /**
     * Set ON-WAVE duration in seconds.
     * Range is [0sec,60sec].
     * If out of range, onWave will be set to min/max bound.
     * @param x 
     */
    protected void setOnWave(double x) {
        if(x>60)this.onWave = 60;
        else if(x<0) this.onWave = 0;
        else this.onWave = x;
    }
}

