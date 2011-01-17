/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class CInterClientConnector {

     private static CInterClientConnector instance;

     private boolean roleServer;
     private Socket clientSocket;

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

    public void setRoleServer(boolean roleServer) {
        this.roleServer = roleServer;
    }

    public boolean getRoleServer() {
        return roleServer;
    }

    synchronized public boolean prepareConnection(String hostname, int port) {
        try {
            clientSocket = new Socket(hostname, port);
            System.out.println("Standard connection completed");
        } catch (UnknownHostException ex) {
            Logger.getLogger(CInterClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CInterClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

}
