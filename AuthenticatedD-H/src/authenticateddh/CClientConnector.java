/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CPacket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Sebastian
 */
public class CClientConnector extends Thread {
    private static CClientConnector instance;

    // Gniazdko klienta
    private Socket clientSocket;
    private SocketChannel sChannel;

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
    synchronized public boolean connect(String ip)
    {
        
        try {
            sChannel = SocketChannel.open();
            sChannel.configureBlocking(true);

            System.out.println("Connection started");
            if(standardConnect(CClientConstraints.SERVER_IP, CClientConstraints.TCP_PORT)) {
                System.out.println("Standard connection completed");
            }
            standardCommunication();
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
    synchronized private boolean standardConnect(String hostname, int port) throws UnknownHostException, IOException
    {
        return sChannel.connect(new InetSocketAddress(hostname, port));
        //clientSocket = new Socket( hostname, port );
        
    }

    synchronized private void disconnect() throws UnknownHostException, IOException
    {
        sChannel.close();
        System.out.println("Disconnected");
    }

    //komunikacja
    synchronized private boolean standardCommunication() throws IOException
    {
        clientSocket = sChannel.socket();
        
        ObjectInputStream oInputStream = null;

        //Wysylanie
        ObjectOutputStream oOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        CPacket packetOut = new CPacket();
        CPacket packetIn = new CPacket();

        boolean communication = true;

        boolean result = false;

        try {
            while(communication) {
                if(!CCurrentCommand.getInstance().getCurrentCommand().equals("none")) {
                    packetOut = CCommunicationProtocol.getInstance().processOutput(null);

                    oOutputStream.writeObject(packetOut);
                    oOutputStream.flush();

                    //Odbieranie
                    if(oInputStream == null)
                        oInputStream = new ObjectInputStream(clientSocket.getInputStream());

                    packetIn = (CPacket) oInputStream.readObject();

                    result = CCommunicationProtocol.getInstance().processInput(packetIn);

                    oOutputStream.reset();
                }
                sleep(1000);
            }
            //Zamykanie
            oInputStream.close();
            oOutputStream.close();

        } catch (InterruptedException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
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
