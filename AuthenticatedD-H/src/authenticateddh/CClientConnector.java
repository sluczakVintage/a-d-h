/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Sebastian
 */
public class CClientConnector {
    private static CClientConnector instance;

    // Gniazdko klienta
    Socket clientSocket;

    //Singleton
    private CClientConnector()
    {
        System.out.println("CConnector");
    }

    public static synchronized CClientConnector getInstance() {
	if (instance == null) {
		instance = new CClientConnector();
	}
	return instance;
    }

    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }
    //polaczenie
    synchronized public boolean connect(String ip, String reason)
    {
        try {
            System.out.println("Connection started");
            standardConnect(CClientConstraints.SERVER_IP, CClientConstraints.TCP_PORT);
            standardCommunication(reason);
            disconnect();
        } catch (UnknownHostException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    //podlaczenie
    synchronized private void standardConnect(String hostname, int port) throws UnknownHostException, IOException
    {
        clientSocket = new Socket( hostname, port );
        System.out.println("Standard connection completed");
    }

    synchronized private void disconnect() throws UnknownHostException, IOException
    {
        clientSocket.close();
        System.out.println("Disconnected");
    }

    //komunikacja
    synchronized private boolean standardCommunication(String command) throws IOException
    {
        ObjectInputStream oInputStream = null;
        ObjectOutputStream oOutputStream = null;

        CPacket packetOut = new CPacket();
        CPacket packetIn = new CPacket();

        boolean result = false;

        try {
                //Wysylanie
                oOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                packetOut = CCommunicationProtocol.getInstance().processOutput(command);

                oOutputStream.writeObject(packetOut);
                oOutputStream.flush();

                //Odbieranie
                oInputStream = new ObjectInputStream(clientSocket.getInputStream());

                packetIn = (CPacket) oInputStream.readObject();

                result = CCommunicationProtocol.getInstance().processInput(packetIn);

                //Zamykanie
                oInputStream.close();
                oOutputStream.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            System.err.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
