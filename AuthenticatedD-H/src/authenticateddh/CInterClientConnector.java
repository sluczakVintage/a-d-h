/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.net.Socket;
import java.util.TreeMap;

/**
 *
 * @author Sebastian
 */
public class CInterClientConnector {

     private TreeMap<Integer, CInterClientCommunicationThread> threadMap = new TreeMap<Integer, CInterClientCommunicationThread>();
     
     private static CInterClientConnector instance;

     private boolean listening;

     private CInterClientConnector()
    {

        System.out.println("CInterClientConnector");
    }

    public static synchronized CInterClientConnector getInstance() {
	if (instance == null) {
		instance = new CInterClientConnector();
	}
	return instance;
    }

    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    synchronized public boolean prepareConnection(int ID) {
        CInterClientCommunicationThread tempThread = new CInterClientCommunicationThread(CFriendUserManager.getInstance().getUser(ID).getInetAddress().getHostAddress(), CClientConstraints.TCP_PORT + ID, ID);
        tempThread.start();
        threadMap.put(ID, tempThread);
        threadMap.get(ID).startThread();
        CConnectionResolver.getInstance().setConnectionProperty(ID, false);

        return true;
    }
       

    public void executeAction(int ID, CCommandType command, String message) {
        threadMap.get(ID).setCommand(command);
        threadMap.get(ID).setFriendID(ID);
        threadMap.get(ID).setMessage(message);
        threadMap.get(ID).setSend(true);
    }


}


