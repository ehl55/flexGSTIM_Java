package flexGSTIMGUIs;

import channel_package.Channel;
import java.util.List;
import javax.swing.JTextArea;

/**
 * This sub-GUI is instantiated by the MainWindow GUI. 
 * Used to display the current stimulation pattern in text form.
 * @author elee1
 */
public class ViewPatternWindow extends javax.swing.JFrame {
    
    MainWindow mainWin;
    List<Channel> cl; //channelList from mainWin
    int nOW; //numOnWave from mainWin

    /**
     * Creates new form ViewPatternWindow. MainWindow passed as an argument
     * so that we can send info to it.
     * @param m 
     */
    public ViewPatternWindow(MainWindow m) {
        initComponents();
        
        this.mainWin = m;
        this.cl = mainWin.getChannelList();
        this.nOW = mainWin.getNumOnWave(); 
        
        fillTxt();
    }
    
    /**
     * Fills the JTextArea with a text-based pattern display.
     */
    private void fillTxt() {
        
        append("The following pattern will repeat indefinitely: \n\n");
        
        for (int i = 0; i < cl.size(); i++) {//loop through channels
            Channel ci = cl.get(i);
            append(ci.toString() + ": ");
            for(int k = 0; k < i; k++) append(space()+" ");
            
            //add ON-WAVE intervals
            for (int j = 0; j < nOW; j++) {
                if(ci.getOnWave()>=10) {//two digits
                    append("|----" + ci.getOnWave() + " sec----|");
                }
                else{//1 digit, needs extra space
                    append("|----" + ci.getOnWave() + "  sec----|");
                }
                append(space());
            }
            
            //because rest intervals are 1 space longer
            for(int x = 0; x < i; x++) append(" ");
                            
            //add rest interval
            if(mainWin.getRest()>=10) {//two digits
                append("|--Rest " + mainWin.getRest() + " min--|\n");
            }
            else {//1 digit
                append("|--Rest " + mainWin.getRest() + "  min--|\n");
            }
                
        }
    }
    
    /**
     * Helper to align intervals w/ proper spacing.
     * @return 
     */
    private String space() {
        return "              "; //14 spaces
    }
    
    /**
     * Helper to append text DIRECTLY to patternTxt JTextArea.
     * @param txt 
     */
    private void append(String str) {
        patternTxt.setText(patternTxt.getText()+str);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        patternTxt = new javax.swing.JTextArea();
        ok = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Stimulation Pattern");

        patternTxt.setColumns(20);
        patternTxt.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        patternTxt.setRows(5);
        jScrollPane1.setViewportView(patternTxt);

        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(483, 483, 483)
                .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(504, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(ok)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when user presses "OK". Don't need to save anything, simply
     * dispose of ViewPatternWindow.
     * @param evt 
     */
    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        this.dispose();
    }//GEN-LAST:event_okActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton ok;
    private javax.swing.JTextArea patternTxt;
    // End of variables declaration//GEN-END:variables
}
