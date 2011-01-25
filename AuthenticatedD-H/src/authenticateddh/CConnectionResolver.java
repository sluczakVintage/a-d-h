package authenticateddh;

import java.util.TreeMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sebastian
 */
public class CConnectionResolver {

    private static CConnectionResolver instance;

    private TreeMap<Integer, Boolean> connectionMap;

    private CConnectionResolver()
    {
        connectionMap = new TreeMap<Integer, Boolean>();
        
        System.out.println("CConnectionResolver");
    }

    public static synchronized CConnectionResolver getInstance() {
	if (instance == null) {
		instance = new CConnectionResolver();
	}
	return instance;
    }
    
    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    synchronized public void setConnectionProperty(int ID, boolean type) {
        System.out.println("Adding connection as " + type);
        connectionMap.put(ID, type);
    }

    synchronized public void removeConnectionProperty(int ID) {
        System.out.println("Removing connection " + ID);
        connectionMap.remove(ID);
    }
    //true is server
    //false is client
    synchronized public void resolveConnectionSendMessage(int ID, CCommandType command, String message) {

        boolean connectionType;

        if(connectionMap.containsKey(ID)) {
            connectionType = connectionMap.get(ID);
            System.out.println("Polaczenie typu: " + connectionType);
            if(connectionType)
                CInterClientConnectorServer.getInstance().executeAction(ID, command, message);
            else
                CInterClientConnector.getInstance().executeAction(ID, command, message);
        }
        else {
            System.err.println("Nie ma takiego polaczenia!!");
        }
    }
}
