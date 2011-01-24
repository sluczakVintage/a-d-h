/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;


/**
 *
 * @author Sebastian
 */
public class Main {


 private ClientDHApp1 clientApp;
    /**
     * @param args the command line arguments
     */
    public Main() {
        try {
        initGui();
        } catch (IOException ex) {

        }
    }
    private void initGui() throws IOException {

        clientApp = ClientDHApp1.getInstance();
        new Thread(clientApp).start();
        //clientApp.run();

        CClientConstraints.getInstance().setPasswordHash("haslo");

    }

    public static void main(String[] args) throws Exception {

        System.out.println("KLIENT");


        new Main();

        SecureRandom random = new SecureRandom();
        BigInteger cipher = new BigInteger(128, random);
        //BigInteger cipher = new BigInteger
        String message = "Malina jest najmadrzejszy";
        System.out.println(message);
        String encrypted = CClientConstraints.encryptMessage(cipher, message);
        System.out.println(encrypted);
        String decrypted = CClientConstraints.decryptMessage(cipher, encrypted);
        System.out.println(decrypted);

//
//        while(true)
//        {
//            try {
//            Thread.sleep(100);
//            } catch (InterruptedException ex) {
//
//            }
//
//        }
    }

}


