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
public class CInterClientCommunicationThread extends Thread{

    private SocketChannel sChannel;

    //dane do polaczenia
    private String hostname;
    private int port;
    private int friendID = 0;


    private CInterClientCommunicationProtocol cInterClientCommunicationProtocol;

    //zmienne uzywane przy transmisji
    private boolean send;
    private String message;
    private CCommandType command;
    
    private boolean communication = false;
    private Socket clientSocket;

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
    //command decyduje o pierwszej wiadomosci
    public CInterClientCommunicationThread(String hostname, int port, int friendID,  CCommandType command) {
	super("CInterClientCommunicationThread");

        this.hostname = hostname;
        this.port = port;
        this.friendID = friendID;
        this.command = command;

        cInterClientCommunicationProtocol = new CInterClientCommunicationProtocol(friendID);
        System.out.println("Attempting connection to " + hostname + ":" + port);

    }

    public void stopThread() {
        if(communication)
            disconnect();
        communication = false;
    }

    public void startThread() {
        communication = true;
    }

    //polaczenie
    @Override
    public void run()

    {
        //petla ciagla
        while(true) {
            //jesli komunikacja ma trwac
            if(communication) {
                try {
                    sChannel = SocketChannel.open();
                    sChannel.configureBlocking(true);

                    System.out.println("Connection started");
                    if(standardConnect(hostname, port)) {
                        System.out.println("Standard connection completed");
                        
                        standardCommunication();
                    }
                    else
                    {
                        System.out.println("Unable to connect");
                        communication = false;
                    }


                } catch (UnknownHostException ex) {
                    Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                    //return false;
                } catch (IOException ex) {
                    Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
                    ex.printStackTrace();
                    //return false;
                }
                System.out.println("watek przerwany");
            }
            //przespij sie
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //CConnectionResolver.getInstance().removeConnectionProperty(friendID);
        
    }

    //podlaczenie
    synchronized private boolean standardConnect(String hostname, int port)
    {
        try {
            return sChannel.connect(new InetSocketAddress(hostname, port));
            //clientSocket = new Socket( hostname, port );
        } catch (IOException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    private void disconnect()
    {
        try {
            sChannel.close();
            System.out.println("Disconnected");
            ClientDHApp1.getInstance().toggleButtons(false);
        } catch (IOException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //komunikacja
    synchronized private boolean standardCommunication() throws IOException
    {
        System.out.println("standardCommunication");
        clientSocket = sChannel.socket();
        boolean result = false;

        CPacket packetOut = new CPacket();
        CPacket packetIn = new CPacket();

        ObjectInputStream oInputStream = null;
        ObjectOutputStream oOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        //jesli jestesmy w fazie powitania
        if(command == CCommandType.CT_HELLO) {


            try {
            //Wysylanie
            packetOut = cInterClientCommunicationProtocol.processOutput(command, message, friendID);
            oOutputStream.writeObject(packetOut);
            oOutputStream.flush();
            oOutputStream.reset();
            //Odbieranie
            if(oInputStream == null)
                oInputStream = new ObjectInputStream(clientSocket.getInputStream());
            packetIn = (CPacket) oInputStream.readObject();
            cInterClientCommunicationProtocol.processInput(packetIn);
            command = CCommandType.CT_NONE;
            }  catch (ClassNotFoundException ex) {
                Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                System.err.println(ex.getMessage());
            } catch (NullPointerException ex) {
                System.err.println(ex.getMessage());
            }
        }

         while(communication) {
            try {
                if (command == CCommandType.CT_MESSAGE) {
                    packetOut = cInterClientCommunicationProtocol.processOutput(command, message, friendID);
                    oOutputStream.writeObject(packetOut);
                    oOutputStream.flush();
                    oOutputStream.reset();
                    command = CCommandType.CT_NONE;
                }
                sleep(1000);

            } catch (InterruptedException ex) {
                Logger.getLogger(CInterClientCommunicationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
    //Zamykanie
    oInputStream.close();
    oOutputStream.close();
    System.out.println("komunikacja zakonczona");

        ////KONIEC HELLO
//            while(communication) {
//                if(!(command == CCommandType.CT_NONE)) {
//                    packetOut = cInterClientCommunicationProtocol.processOutput(command, message, friendID);
//
//                    oOutputStream.writeObject(packetOut);
//                    oOutputStream.flush();
//
//                    //Odbieranie
//                    if(oInputStream == null)
//                        oInputStream = new ObjectInputStream(clientSocket.getInputStream());
//
//                    packetIn = (CPacket) oInputStream.readObject();
//
//                    cInterClientCommunicationProtocol.processInput(packetIn);
//                    command = CCommandType.CT_NONE;
//
//                    oOutputStream.reset();
//
//                    while(command == CCommandType.CT_NONE ) {
//                         if(!communication) {
//                            return result;
//                        }
//                        sleep(100);
//                    }
//                }
//                if(!communication) {
//
//                        return result;
//                }
//                sleep(1000);
//            }
//            //Zamykanie
//            oInputStream.close();
//            oOutputStream.close();
//
//        }  catch (ClassNotFoundException ex) {
//            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SocketException ex) {
//            System.err.println(ex.getMessage());
//        } catch (NullPointerException ex) {
//            System.err.println(ex.getMessage());
//        }
//        System.out.println("komunikacja zakonczona");
        return result;
    }

}






