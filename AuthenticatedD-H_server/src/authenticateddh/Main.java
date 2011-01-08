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

 private CServerConnector cServerConnector;
    /**
     * @param args the command line arguments
     */
    public Main() {
        try {
        initServer();
        } catch (IOException ex) {

        }
    }
    private void initServer() throws IOException {

        cServerConnector = CServerConnector.getInstance();
        new Thread(cServerConnector).start();
    }

    public static void main(String[] args) {

        System.out.println("SERWER");
        new Main();

        while(true)
        {
            try {
            Thread.sleep(100);
            } catch (InterruptedException ex) {

            }

        }
    }

}


