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

    private CInterClientServerCommunicationProtocol cInterClientCommunicationProtocol;

    //zmienne uzywane przy transmisji
    private String message;
    private CCommandType command;
    private int friendID = 0;

    private boolean communication = true;

    public void setCommand(CCommandType command) {
        this.command = command;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
        cInterClientCommunicationProtocol.setThreadNo(friendID);
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public CInterClientCommunicationServerThread(SocketChannel serverSocketChannel, int threadNo ) {
	super("CInterClientCommunicationThread");

	this.socket = serverSocketChannel.socket();
        this.sourceAddress = socket.getInetAddress();
        this.friendID = threadNo;

        cInterClientCommunicationProtocol = new CInterClientServerCommunicationProtocol(friendID);
        
        System.out.println("Accepted connection from " + sourceAddress);

        try {

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

       try {
            oInputStream = new ObjectInputStream(socket.getInputStream());
            oOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //jesli po prostu sie witamy, to
            //powitanie!
                packetIn = (CPacket) oInputStream.readObject();
                command = cInterClientCommunicationProtocol.processInput(packetIn);
                if (command == CCommandType.CT_HELLO) {
                try {
                    packetOut = cInterClientCommunicationProtocol.processOutput(command, message, friendID);
                    command = CCommandType.CT_MESSAGE;
                    oOutputStream.writeObject(packetOut);
                    oOutputStream.flush();
                    oOutputStream.reset();
                    ////Temporarly removed
                    //CConnectionResolver.getInstance().removeConnectionProperty(friendID);
                }  catch (IOException ex) {
                    Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //gdy hello nie bylo nigdy wiadomoscia, to serwer tak czy siak zaczyna komunikacje
            //Dopoki trwa komunikacja
            while (communication) {
                if (command == CCommandType.CT_MESSAGE) {
                        packetIn = (CPacket) oInputStream.readObject();
                        cInterClientCommunicationProtocol.processInput(packetIn);
                }
                sleep(1000);
            }
        }
        catch (InterruptedException ex) {
            Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (ClassNotFoundException ex) {
            Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
        System.out.println("Killing Thread");
            Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                //Zamykanie
                oInputStream.close();
                oOutputStream.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(CInterClientCommunicationServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setCommunication(boolean communication) {
        this.communication = communication;
    }
}


