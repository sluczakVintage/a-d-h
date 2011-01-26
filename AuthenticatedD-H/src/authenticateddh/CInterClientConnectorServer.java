/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class CInterClientConnectorServer extends Thread{


    static private CInterClientConnectorServer instance;

    private ServerSocketChannel serverChannel;
    private int workingPort;
    private boolean listening = false;
    //mapa watkow
    private TreeMap<Integer, CInterClientCommunicationServerThread> threadMap = new TreeMap<Integer, CInterClientCommunicationServerThread>();



    private CInterClientConnectorServer() 
    {
       System.out.println("CServerConnector");
    }

    public static synchronized CInterClientConnectorServer getInstance()  {
	if (instance == null) {
		instance = new CInterClientConnectorServer();
	}
	return instance;
    }

   /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    public void run() {

        try {
        int i = 1000;
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(true);

        serverChannel.socket().bind(new InetSocketAddress(workingPort));

        System.out.println("Listening on " + workingPort );
        
        while (listening) {
            CInterClientCommunicationServerThread tempThread = new CInterClientCommunicationServerThread(serverChannel.accept(), i );
            tempThread.start();
            threadMap.put(i, tempThread);
            i++;
            sleep(1000);
        }

        serverChannel.close();
        }
        catch (InterruptedException ex) {
            Logger.getLogger(CInterClientConnectorServer.class.getName()).log(Level.SEVERE, null, ex);
        }         catch ( IOException ex) {
            System.err.println("IO Exception " + ex.getMessage());
       }
    }

    public void stopServerConnector() {
        listening = false;
    }

    public void startServerConnector() {
        this.workingPort = CClientConstraints.TCP_PORT + CClientConstraints.getInstance().getID();
        listening = true;
    }

    public boolean getListeningState() {
        return listening;
    }

    public void executeAction(int windowNo, CCommandType command, String message) {
        threadMap.get(windowNo).setCommand(command);
        threadMap.get(windowNo).setMessage(message);
    }

    public void killThread() {
        Collection c = threadMap.values();
        Iterator itr = c.iterator();

        while(itr.hasNext()) {
            CInterClientCommunicationServerThread tempThread = ((CInterClientCommunicationServerThread)(itr.next()));
            tempThread.setCommunication(false);
            System.out.println("Stopping thread");
        }
    }

    public void setFriendID(int oldFriendNo, int friendID) {
        //przydzielić odpowiedni numerek okna
        CInterClientCommunicationServerThread thread = threadMap.get(oldFriendNo);
        threadMap.remove(oldFriendNo);
        thread.setFriendID(friendID);
        ////Temporarly removed
        //CConnectionResolver.getInstance().setConnectionProperty(friendID, true);
        threadMap.put(friendID,thread);

    }
}
