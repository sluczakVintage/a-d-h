/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MessageWindow.java
 *
 * Created on 2011-01-22, 13:13:02
 */

package authenticateddh;

/**
 *
 * @author malina
 */
public class MessageWindow extends javax.swing.JFrame {

    /** Creates new form MessageWindow */
    private int ID_;
    private String nickname_;

    public MessageWindow() {
        //initComponents();
    }

    public MessageWindow(int ID_, String nickname_) {
        this.ID_ = ID_;
        this.nickname_ = nickname_;
        setTitle("Rozmowa z " + nickname_);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMessages = new javax.swing.JTextArea();
        jButtonWyslij = new javax.swing.JButton();
        jButtonZamknij = new javax.swing.JButton();
        jTextFieldMessage = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Treść rozmowy:");

        jTextAreaMessages.setColumns(20);
        jTextAreaMessages.setEditable(false);
        jTextAreaMessages.setRows(5);
        jScrollPane1.setViewportView(jTextAreaMessages);

        jButtonWyslij.setText("Wyślij");
        jButtonWyslij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWyslijActionPerformed(evt);
            }
        });

        jButtonZamknij.setText("Zamknij");
        jButtonZamknij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonZamknijActionPerformed(evt);
            }
        });

        jTextFieldMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMessageActionPerformed(evt);
            }
        });

        jLabel2.setText("Wiadomość:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonWyslij, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(jButtonZamknij, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonZamknij, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                    .addComponent(jButtonWyslij, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonZamknijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonZamknijActionPerformed
        closeWindow();
    }//GEN-LAST:event_jButtonZamknijActionPerformed

    private void jButtonWyslijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWyslijActionPerformed
        String text = jTextFieldMessage.getText();
        if (!text.equals("")){
            createMessage("Ja", text);
        }
        // TODO tutaj trzeba wstawić wysłanie wiadomości do innego Usera
    }//GEN-LAST:event_jButtonWyslijActionPerformed

    private void jTextFieldMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMessageActionPerformed
        String text = jTextFieldMessage.getText();
        if (!text.equals("")){
            createMessage("Ja", text);
        }        
        // TODO tutaj trzeba wstawić wysłanie wiadomości do innego Usera
    }//GEN-LAST:event_jTextFieldMessageActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        closeWindow();
    }//GEN-LAST:event_formWindowClosing


    public void createMessage(String kto, String wiadomosc) {
        jTextAreaMessages.append(kto + ": " + wiadomosc + "\n");
        jTextFieldMessage.setText("");
        jTextAreaMessages.setCaretPosition(jTextAreaMessages.getDocument().getLength());
    }

    public int closeWindow(){
        ClientDHApp1.getInstance().cMessageWindowsMap.remove(ID_);
        dispose();
        return 1;
    }
    /**
    * @param args the command line arguments
    */
    //public static void main(String args[]) {
        //java.awt.EventQueue.invokeLater(new Runnable() {
           // public void run() {
                //initComponents();
               // new MessageWindow().setVisible(true);
           // }
        //});
   // }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonWyslij;
    private javax.swing.JButton jButtonZamknij;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaMessages;
    private javax.swing.JTextField jTextFieldMessage;
    // End of variables declaration//GEN-END:variables

}
