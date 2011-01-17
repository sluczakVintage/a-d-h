/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Sebastian
 */
public class CInterClientConnectorServer {

     private static CInterClientConnectorServer instance;

     private boolean roleServer;
     private Socket clientSocket;

     private CInterClientConnectorServer()
    {

        System.out.println("CInterClientConnector");
    }

    public static synchronized CInterClientConnectorServer getInstance() {
	if (instance == null) {
		instance = new CInterClientConnectorServer();
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

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (NullPointerException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

        clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        System.out.println("Connection as server completed");

        return true;
    }

}
