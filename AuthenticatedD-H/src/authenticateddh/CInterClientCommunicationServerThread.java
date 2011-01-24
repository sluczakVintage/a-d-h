/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;


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
public class CInterClientCommunicationServerThread extends Thread{

    private Socket socket = null;
    private InetAddress sourceAddress;
    private ObjectInputStream oInputStream = null;
    private ObjectOutputStream oOutputStream = null;
    private int threadNo = 0;

    private CInterClientServerCommunicationProtocol cInterClientCommunicationProtocol = new CInterClientServerCommunicationProtocol(threadNo);

    //zmienne uzywane przy transmisji
    private boolean send;
    private String message;
    private CCommandType command = CCommandType.CT_NONE;
    private int friendID = 0;

    public void setCommand(CCommandType command) {
        this.command = command;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSend(boolean send) {
        this.send = send;

    }
    public CInterClientCommunicationServerThread(SocketChannel serverSocketChannel, int threadNo) {
	super("CInterClientCommunicationThread");

	this.socket = serverSocketChannel.socket();
        this.sourceAddress = socket.getInetAddress();
        this.threadNo = threadNo;

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
        System.out.println("ruszylem!");
        boolean communication = true;
        //send = true;

        try {

            while(communication) {
                //Odbieranie
                if(!send) {
                    packetIn = (CPacket) oInputStream.readObject();
                    command = cInterClientCommunicationProtocol.processInput(packetIn);
                    send = !send;
                }

                //Wysylanie
                if(send)
                    packetOut = cInterClientCommunicationProtocol.processOutput(command, message, friendID);

                oOutputStream.writeObject(packetOut);
                oOutputStream.flush();
                oOutputStream.reset();
                sleep(100);

                if(!CInterClientConnectorServer.getInstance().getListeningState())
                    communication = false;
            }
            //Zamykanie
            oInputStream.close();
            oOutputStream.close();

            socket.close();


        } catch (InterruptedException ex) {
            Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
            //disconnect client
        }
        System.out.println("Killing Thread");
    }

}
