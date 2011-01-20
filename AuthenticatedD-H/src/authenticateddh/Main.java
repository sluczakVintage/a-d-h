/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;


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

        CClientConstraints.getInstance().setPasswordHash("haslo");

    }

    public static void main(String[] args) {

        System.out.println("SERWER");


        new Main();
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


