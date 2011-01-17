/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 *
 * @author Sebastian
 */
public class CServerConnector implements Runnable{


    static private CServerConnector instance;

    private ServerSocketChannel serverChannel;
    
    private boolean listening;

    private CServerConnector() throws IOException
    {
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

        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(true);

        serverChannel.socket().bind(new InetSocketAddress(ServerConstraints.TCP_PORT));

        
        while (listening)
            new CServerConnectorThread(serverChannel.accept()).start();

        serverChannel.close();
        }
         catch ( IOException ex) {
            System.err.println("IO Exception " + ex.getMessage());
       }
    }
}
