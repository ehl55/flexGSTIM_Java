package flexGSTIMGUIs;

import channel_package.Channel;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * This sub-GUI is instantiated by the MainWindow GUI. Used to update specified
 * channel's settings (amp,dur,freq,on-wave). 
 * @author elee1
 */
public class ChannelSettingsWindow extends javax.swing.JFrame {
    
    private List<Channel> channelList;
    private MainWindow mainWin;
    
    /**
     * Creates new form ChannelWindowAdvancedSettings. MainWindow GUI object is passed
     * as an argument so that we can send information to it. 
     * @param m the MainWindow
     */
    public ChannelSettingsWindow(MainWindow m) {
        initComponents();   
        
        mainWin = m;
        channelList = m.getChannelList();
        
        //update the channel selection JComboBox with correct channel options
        updateChannelSelection();
    }
    
    /**
     * Populates the channel selection JComboBox with options from channelList
     */
    private void updateChannelSelection() {
        channelSelect.removeAllItems(); //reset JComboBox
        
        for (Channel c : channelList) {
            channelSelect.addItem(c.toString());
        }
    }
    
    /**
     * Updates amp, dur, freq, on-wave JTextFields to display correct info.
     * Useful for when user selects a channel from channelSelect JComboBox.
     */
    private void updateTxtBoxes() {
        int i = channelSelect.getSelectedIndex();
        
        if(i==-1) return; //sometimes channelSelect event is accidentally triggered
                          //which calls updateTxtBoxes(),even when selected index is -1. 
                          //This line of code avoids index out of bounds error
        
        Channel c = channelList.get(i);
        
        ampBox.setText(""+c.getAmp());
        durBox.setText(""+c.getDur());
        freqBox.setText(""+c.getFreq());
        owBox.setText(""+c.getOnWave());  
    }
    
    /**
     * Updates the selected Channel object with information from the
     * amp, dur, freq, on-wave JTextFields
     */
    private void updateChannelObject() {
        int i = channelSelect.getSelectedIndex();
        Channel c = channelList.get(i);
        
        double a = Double.valueOf(ampBox.getText()) ;
        double d = Double.valueOf(durBox.getText());
        double f = Double.valueOf(freqBox.getText());
        double o = Double.valueOf(owBox.getText());
        
        c.updateSettings(a, d, f, o);
    }
    
    /**
     * Checks that JTextFields corresponding to amp, dur, freq, on-wave are in proper ranges.
     * @return true if all JTextFields are in range. Otherwise, pops up error message and returns false
     */
    private boolean checkRange() {
        String notInRange = ""; //used for displaying error message
        
        double a = Double.valueOf(ampBox.getText());
        int d = Integer.valueOf(durBox.getText());
        int f = Integer.valueOf(freqBox.getText());
        int ow = Integer.valueOf(owBox.getText());
        
        if(a>8.128 || a<0) notInRange += "Amplitude not in range [0mA,8.128mA]\n";
        if(d>423 || d<0) notInRange += "Pulse duration not in range [0uS,423uS]\n";
        if(f>100 || f<0) notInRange += "Frequency not in range [0Hz,100Hz]\n";
        if(ow>60 || ow<0) notInRange += "ON-WAVE duration not in range [0sec,60sec]\n";
        
        if(notInRange.length()>0) {
            notInRange += "\nInput within proper ranges and try again";
            JOptionPane.showMessageDialog(null, 
                    notInRange, 
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ampBox = new javax.swing.JTextField();
        durBox = new javax.swing.JTextField();
        freqBox = new javax.swing.JTextField();
        owBox = new javax.swing.JTextField();
        amp = new javax.swing.JLabel();
        dur = new javax.swing.JLabel();
        freq = new javax.swing.JLabel();
        ow = new javax.swing.JLabel();
        applyButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        channelSelect = new javax.swing.JComboBox<>();
        defaultButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Channel Settings");

        amp.setText("Amplitude (mA)");

        dur.setText("Pulse Width (uS)");

        freq.setText("Frequency (Hz)");

        ow.setText("ON-WAVE Duration (sec)");

        applyButton.setText("Apply");
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Channel Selection");

        channelSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                channelSelectActionPerformed(evt);
            }
        });

        defaultButton.setText("Default Settings");
        defaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(defaultButton)
                .addGap(133, 133, 133))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(amp)
                                    .addComponent(dur)
                                    .addComponent(freq)
                                    .addComponent(ow))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(owBox, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(freqBox, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(durBox, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ampBox, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(applyButton)
                        .addGap(18, 18, 18)
                        .addComponent(okButton)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(channelSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cancelButton))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(defaultButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(channelSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ampBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amp))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(durBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dur))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(freqBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(freq))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(owBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ow))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(applyButton)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addGap(30, 30, 30))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fill in amp, dur, freq, on-wave JTextFields with default settings
     * @param evt 
     */
    private void defaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultButtonActionPerformed
        ampBox.setText("5");
        durBox.setText("150");
        freqBox.setText("20");
        owBox.setText("15"); 
    }//GEN-LAST:event_defaultButtonActionPerformed

    /**
     * Updates selected Channel object w/ info put into amp, dur, freq, on-wave JTextFields
     * and disposes of ChannelSettingsWindow.
     * @param evt 
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        
        if(checkRange()) {
            //save changes to channel object before disposing GUI
            updateChannelObject();
            this.dispose();
        }
    }//GEN-LAST:event_okButtonActionPerformed

    /**
     * User made a selection from channelSelect JComboBox, update amp, dur, freq, on-wave JTextFields
     * with corresponding channel information
     * @param evt 
     */
    private void channelSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_channelSelectActionPerformed
        updateTxtBoxes();
    }//GEN-LAST:event_channelSelectActionPerformed

    /**
     * Updates selected Channel object w/ info inputted into amp, dur, freq, on-wave JTextFields. 
     * @param evt 
     */
    private void applyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonActionPerformed
        
        if(checkRange()) {
            //save changes to channel object
            updateChannelObject();
        }
    }//GEN-LAST:event_applyButtonActionPerformed

    /**
     * Dispose of GUI without saving any changes
     * @param evt 
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amp;
    private javax.swing.JTextField ampBox;
    private javax.swing.JButton applyButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> channelSelect;
    private javax.swing.JButton defaultButton;
    private javax.swing.JLabel dur;
    private javax.swing.JTextField durBox;
    private javax.swing.JLabel freq;
    private javax.swing.JTextField freqBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel ow;
    private javax.swing.JTextField owBox;
    // End of variables declaration//GEN-END:variables
}
