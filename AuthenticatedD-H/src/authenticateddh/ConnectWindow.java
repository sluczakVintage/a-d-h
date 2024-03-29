/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConnectWindow.java
 *
 * Created on 2011-01-23, 20:27:29
 */

package authenticateddh;

import javax.swing.JOptionPane;

/**
 *
 * @author malina
 */
public class ConnectWindow extends javax.swing.JFrame {

    private static ConnectWindow instance;

    /** Creates new form ConnectWindow */

    private ConnectWindow()
    {
        initComponents();
        setTitle("Rejestracja");
        System.out.println("ConnectWindow");
    }

    /**
     *
     * @return
     */
    public static synchronized ConnectWindow getInstance() {
	if (instance == null) {
		instance = new ConnectWindow();
	}
	return instance;
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonConnect = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPasswordField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldAdress = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Login:");

        jLabel2.setText("Password:");

        jButtonConnect.setText("Connect");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel3.setText("Adres Serwera:");

        jTextFieldAdress.setText("localhost");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAdress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jButtonConnect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(loginTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jButtonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldAdress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConnect)
                    .addComponent(jButtonCancel))
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        String login = loginTextField.getText();
        String pass = jPasswordField.getText().toString();
        String address = jTextFieldAdress.getText();
        if(login.equals(null)|| login.equals("") || pass.equals(null) || pass.equals("")){
            JOptionPane.showMessageDialog(null, "Proszę wpisać ID i hasło", "Błąd!!", JOptionPane.ERROR_MESSAGE);
        }
        else{
            CClientConstraints.getInstance().setNickname(login);
            ClientDHApp1.getInstance().setUserText(login);
            ClientDHApp1.getInstance().setTitle(login);
            CClientConstraints.getInstance().setPasswordHash(pass);
            CClientConstraints.getInstance().setSERVER_IP(address);
            CClientConnector.getInstance().startThread();
            CCurrentCommand.getInstance().setCurrentCommand(CCommandType.CT_REGISTER);
        }
       // if(loginTextField// TODO add your handling code here:
    }//GEN-LAST:event_jButtonConnectActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCancelActionPerformed

    public void showConnectionCompleted() {
            JOptionPane.showMessageDialog(null, "Udało się podłączyć do serwera", "Brawo", JOptionPane.INFORMATION_MESSAGE);
            ClientDHApp1.getInstance().toggleButtons(true);
            dispose();
    }

    public void showConnectionFailed() {
            ClientDHApp1.getInstance().toggleButtons(false);
            JOptionPane.showMessageDialog(null, "Błędne dane logowania, lub nielegalna próba ponownego zalogowania", "Błąd", JOptionPane.ERROR_MESSAGE);
    }
    /**
    * @param args the command line arguments
    */
   // public static void main(String args[]) {
        //java.awt.EventQueue.invokeLater(new Runnable() {
           // public void run() {
              //  new ConnectWindow().setVisible(true);
            //}
       // });
    //}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldAdress;
    private javax.swing.JTextField loginTextField;
    // End of variables declaration//GEN-END:variables

}
