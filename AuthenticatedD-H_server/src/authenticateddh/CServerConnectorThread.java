/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class CServerConnectorThread extends Thread{

    private Socket socket = null;
    private InetAddress sourceAddress;
    private ObjectInputStream oInputStream = null;
    private ObjectOutputStream oOutputStream = null;
    private CCommunicationProtocolServer cCommunicationProtocolServer = new CCommunicationProtocolServer();


    public CServerConnectorThread(Socket socket) {
	super("CServerConnectionThread");
	this.socket = socket;
        this.sourceAddress = socket.getInetAddress();
        System.out.println("Accepted connection from " + sourceAddress);
        try {
            oInputStream = new ObjectInputStream(socket.getInputStream());
            oOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch(Exception e1) {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return;
        }

    }

    public void run() {

        CPacket packetOut = new CPacket();
        CPacket packetIn = new CPacket();
        String command;

        try {

            //Odbieranie
            packetOut = (CPacket) oInputStream.readObject();

            command = cCommunicationProtocolServer.processInput(packetOut);

            //Wysylanie
            packetIn = cCommunicationProtocolServer.processOutput(command);

            oOutputStream.writeObject(packetIn);
            oOutputStream.flush();

            //Zamykanie
            oInputStream.close();
            oOutputStream.close();
            socket.close();

            System.out.println("Killing Thread");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CServerConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CServerConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
