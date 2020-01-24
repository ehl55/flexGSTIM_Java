package flexGSTIMGUIs;

import constants.Mode;
import channel_package.TxtUtil;
import channel_package.DoubleChannel;
import channel_package.Channel;
import channel_package.SingleChannel;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import constants.Mode;
import constants.Status;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;

/**
 * The MainWindow is the "main" GUI that all the sub-GUIs report to.
 * @author elee1
 */
public class MainWindow extends javax.swing.JFrame {
 
    /**Timer variables used to track seconds passed**/
    private Timer timer;
    private int secondsPassed; 
    
    /**Variables related to single/dual channel selection and settings**/
    private List<Channel> channelList; //list of channel objects
    private Mode mode= Mode.SINGLE; //by default single channel mode
    
    /**Variables related to COMM connection**/
    private SerialPort chosenPort;
    
    /**Variables related to rest period**/
    private int rest=20; //by default 20 min 
    
    /**Variables related to ON-WAVE**/
    private int numOnWave=5; //Number of times to repeat ON-WAVE for INDIVIDUAL channel/group
                             //default is 5
    
    /**Variables related to program status**/
    private Status status=Status.OFF; //start OFF
    private Timer togglerTimer; //helps flash the status text
    
    /************Getters************/
    protected List<Channel> getChannelList() {
        return this.channelList;
    }
    protected int getNumOnWave() {
        return this.numOnWave;
    }
    protected int getRest() {
        return this.rest;
    }
    protected SerialPort getChosenPort() {
        return this.chosenPort;
    }
    protected Mode getMode(){
        return this.mode;
    }
    protected Status getStatus(){
        return this.status;
    }
    
    /************Setters************/
    /**
     * Set rest period. Range is 0-99 minutes.
     * Auto set to min/max if out of range.
     * @param r 
     */
    protected void setRest(int r) {
        if(r>99)this.rest=99;
        else if(r<0)this.rest=0;
        else this.rest = r;
    }
    protected void setChosenPort(SerialPort p) {
        this.chosenPort=p;
    }
    protected void setMode(Mode s) {
        this.mode = s;
    }
    
    /************Helper functions************/
    /**
     * Displays the non-selection error
     */
    private void nonSelError() {
        JOptionPane.showMessageDialog(null, 
                    "Channels not selected. \n\nGo to Options → Change Single/Dual Mode.", 
                    "Error", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * An initializer helper function which initializes various class variables 
     */
    private void myInitComponents() {
        
        //application starts off, so button should in OFF-position
        on_button.setEnabled(false);
        
        //starts empty, will be populated when user selects channels/group
        channelList = new ArrayList<Channel>();
                
        //initialize timer variables
        timer = new Timer(1000, new ActionListener() {//1000ms delay → called every second
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
            }
        });
        secondsPassed = 0;
        
        //status logic
        togglerTimer = new Timer(250, new ActionListener() {//250ms delay → called every 0.25 second
            @Override
            public void actionPerformed(ActionEvent e) {
                if(status == Status.ONWAVE) toggleOnWaveStatus();
                if(status == Status.REST) toggleRestStatus();
                if(status == Status.SETUP) toggleSetupStatus();
            }
        });
        togglerTimer.start(); //always running in background  
    }
     /**
     * Helper function which allows channel/group c to continuously 
     * stimulate for an ON-WAVE duration
     * @param c
     * @param pw 
     */
    private void stimForSingleOnWave(Channel c, PrintWriter pw) {
        int waitTime = (int)((1.0/c.getFreq())*1000); //calculate delay neccessary to achieve
                                                      //inputted frequency        
        secondsPassed = 0; //reset
        timer.start(); //begin counting seconds
        
        while(secondsPassed < c.getOnWave()) {
            c.sendStimCommand(pw);
            TxtUtil.wait(waitTime);
        }
        
        timer.stop();
    }  
    /**
     * Helper function which enables a rest period
     */
    private void restPeriod() {
        secondsPassed = 0;
        timer.start();
        
        while(secondsPassed<rest*60 && status == Status.REST){
            TxtUtil.wait(1000); //busy waiting...check every second
        }
        timer.stop();
    }
    /**
     * Helper function which toggles a green "ON-WAVE" text in the status text box.
     */
    private void toggleOnWaveStatus () {
        if(statusTextBox.getForeground()==Color.WHITE) {
            statusTextBox.setForeground(Color.GREEN);
        }
        else {
            statusTextBox.setForeground(Color.WHITE);
        }
    }
    /**
     * Helper function which toggles a blue "REST" text in the status text box
     */
    private void toggleRestStatus() {
        if(statusTextBox.getForeground()==Color.WHITE) {
            statusTextBox.setForeground(Color.BLUE);
        }
        else {
            statusTextBox.setForeground(Color.WHITE);
        }
    }
    /**
     * Helper function which toggles a magenta "SETUP" text in the status text box
     */
    private void toggleSetupStatus() {
        if(statusTextBox.getForeground()==Color.WHITE) {
            statusTextBox.setForeground(Color.MAGENTA);
        }
        else {
            statusTextBox.setForeground(Color.WHITE);
        }
    }
    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        myInitComponents();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        on_button = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        statusTextBox = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        Options = new javax.swing.JMenu();
        Connect = new javax.swing.JMenuItem();
        SingleDual = new javax.swing.JMenuItem();
        ChannelSettings = new javax.swing.JMenuItem();
        RestSettings = new javax.swing.JMenuItem();
        ViewPattern = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("flexGSTIM");

        on_button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toggle_on.png"))); // NOI18N
        on_button.setToolTipText("");
        on_button.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/toggle_off.png"))); // NOI18N
        on_button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                on_buttonMouseClicked(evt);
            }
        });

        statusLabel.setText("Status");

        statusTextBox.setEditable(false);
        statusTextBox.setBackground(java.awt.Color.white);
        statusTextBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        statusTextBox.setForeground(java.awt.Color.red);
        statusTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        statusTextBox.setText("OFF");

        Options.setText("Options");

        Connect.setText("Connect");
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });
        Options.add(Connect);

        SingleDual.setText("Change Single/Dual Mode ");
        SingleDual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SingleDualActionPerformed(evt);
            }
        });
        Options.add(SingleDual);

        ChannelSettings.setText("Change Channel Settings");
        ChannelSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChannelSettingsActionPerformed(evt);
            }
        });
        Options.add(ChannelSettings);

        RestSettings.setText("Change Rest Period");
        RestSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RestSettingsActionPerformed(evt);
            }
        });
        Options.add(RestSettings);

        ViewPattern.setText("View Current Pattern");
        ViewPattern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewPatternActionPerformed(evt);
            }
        });
        Options.add(ViewPattern);

        jMenuBar1.add(Options);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(on_button, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(on_button, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(statusLabel)
                            .addComponent(statusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * User pressed ON-OFF switch. This function contains all the stimulation/rest logic.
     * @param evt 
     */
    private void on_buttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_on_buttonMouseClicked
        
        //User STOPPED stimulations
        if (on_button.isEnabled()) {
            on_button.setEnabled(false);
            
            status=Status.OFF;
            statusTextBox.setForeground(Color.RED);
            statusTextBox.setText("OFF");
        }
        //User BEGAN stimulations
        else {
            
            //first, check that COMM port connection was established
            if(chosenPort == null || !chosenPort.isOpen()) {
                JOptionPane.showMessageDialog(null, 
                        "COMM port connection not established. \n\nGo to Options → Connect.", 
                        "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            //second, check that user populated channelList (by choosing individual channels)
            if(channelList.size()<1) {
                nonSelError();
                return;
            }
            
            //display the green on-button
            on_button.setEnabled(true);
            
            //begin stimulations using a new thread
            Thread thread = new Thread(){
            @Override public void run() {
                PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                
                //First send all the setup commands to device
                status=Status.SETUP;
                statusTextBox.setText("SETUP");
                statusTextBox.setForeground(Color.MAGENTA);
              
                //send setup sequence
                TxtUtil.sendAllCommands("setup.txt", output);
              
                //update settings.txt and send it out
                for(Channel c : channelList) {
                    c.updateSettingsTxtFile();
                }
                TxtUtil.sendAllCommands("settings.txt",output);
                
                //repeat stim+rest until user presses OFF
                int i = 0; //counter to loop through channelList circularly
                int n = channelList.size();
                
                int j = 0; //counter to track elapsed ON-WAVEs
                while(on_button.isEnabled()) {
                    
                    status=Status.ONWAVE; //on-wave status
                    statusTextBox.setText("ON-WAVE");
                    statusTextBox.setForeground(Color.GREEN);
                    stimForSingleOnWave(channelList.get(i),output);
                    
                    //handle i
                    i++;
                    if(i>=n) i = 0;//circle around list
                    
                    //handle j
                    j++;
                    if(j>=n*numOnWave) {//n = 2, numOnWave = 5 by default
                        j=0; //reset
                        
                        status=Status.REST; //rest period status
                        statusTextBox.setForeground(Color.BLUE);
                        statusTextBox.setText("REST");
                        
                        //begin rest period
                        restPeriod();
                    }                 
                }
            }
        };
            thread.start();
        }
    }//GEN-LAST:event_on_buttonMouseClicked
    
    /**
     * User selected Options -> Connect.
     * @param evt 
     */
    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectActionPerformed
        new ConnectWindow(this).setVisible(true);
    }//GEN-LAST:event_ConnectActionPerformed

    /**
     * User selected Options -> Change Single/Dual Mode.
     * @param evt 
     */
    private void SingleDualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SingleDualActionPerformed
        new SingleDualWindow(this).setVisible(true);
    }//GEN-LAST:event_SingleDualActionPerformed

    /**
     * User selected Options -> Change Channel Settings.
     * @param evt 
     */
    private void ChannelSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChannelSettingsActionPerformed
        
        //check that user selected channels
        if(channelList.size()<1) nonSelError();
        else new ChannelSettingsWindow(this).setVisible(true);
        
    }//GEN-LAST:event_ChannelSettingsActionPerformed

    /**
     * User selected Options -> Change Rest Period.
     * @param evt 
     */
    private void RestSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RestSettingsActionPerformed
        new RestSettingsWindow(this).setVisible(true);
    }//GEN-LAST:event_RestSettingsActionPerformed

    /**
     * User selected Options -> View Current Pattern.
     * @param evt 
     */
    private void ViewPatternActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewPatternActionPerformed

        //check that user selected channels
        if(channelList.size()<1) nonSelError();
        else new ViewPatternWindow(this).setVisible(true);      
        
    }//GEN-LAST:event_ViewPatternActionPerformed

    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /*Was originally Nimbus look and feel (commented out code), but I (Eugene) changed it to System look and feel
        because I was having some trouble with the "Design" Tab in Netbeans where preview looked different from run time.*/
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
                UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ChannelSettings;
    private javax.swing.JMenuItem Connect;
    private javax.swing.JMenu Options;
    private javax.swing.JMenuItem RestSettings;
    private javax.swing.JMenuItem SingleDual;
    private javax.swing.JMenuItem ViewPattern;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel on_button;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField statusTextBox;
    // End of variables declaration//GEN-END:variables
}
 
