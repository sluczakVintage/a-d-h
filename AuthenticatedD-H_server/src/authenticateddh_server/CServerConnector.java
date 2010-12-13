/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh_server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Sebastian
 */
public class CServerConnector implements Runnable{


    static private CServerConnector instance;
    private ServerSocket serverSocket;
    private boolean listening;

    private CServerConnector() throws IOException
    {
        serverSocket = null;
        listening = true;
        System.out.println("CServerConnector");
    }

    public static synchronized CServerConnector getInstance() throws IOException {
	if (instance == null) {
		instance = new CServerConnector();
	}
	return instance;
    }

   /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    public void run() {

        try {

        serverSocket = new ServerSocket( ServerConstraints.TCP_PORT );

        while (listening)
            new CServerConnectorThread(serverSocket.accept()).start();

        serverSocket.close();

            ///@todo
        } catch ( IOException ex) {
            System.err.println("IO Exception " + ex.getMessage());
            ///@todo
        }
    }
}
