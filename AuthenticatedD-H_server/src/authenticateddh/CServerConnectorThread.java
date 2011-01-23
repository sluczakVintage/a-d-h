/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;


import authenticateddh.messageformats.CCommand;
import authenticateddh.messageformats.CPacket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
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


    public CServerConnectorThread(SocketChannel serverSocketChannel) {
	super("CServerConnectionThread");
	this.socket = serverSocketChannel.socket();
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
        CCommand command = new CCommand(CCommandType.CT_NONE, 0);
        boolean communication = true;
        
        try {

            while(communication) {
                //Odbieranie
                packetIn = (CPacket) oInputStream.readObject();

                command = cCommunicationProtocolServer.processInput(packetIn, socket);

                //Wysylanie
                packetOut = cCommunicationProtocolServer.processOutput(command);

                oOutputStream.writeObject(packetOut);
                oOutputStream.flush();
                oOutputStream.reset();
                sleep(100);

                if(!CServerConnector.getInstance().getListeningState())
                    communication = false;
            }
            //Zamykanie
            oInputStream.close();
            oOutputStream.close();

            socket.close();


        } catch (InterruptedException ex) {
            Logger.getLogger(CServerConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CServerConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(CServerConnectorThread.class.getName()).log(Level.SEVERE, null, ex);
            CUserManager.getInstance().setUserAvailable(command.getID(), false);
        }
        System.out.println("Killing Thread");
    }


}
