/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CMessage;
import authenticateddh.messageformats.CMessageComm;
import authenticateddh.messageformats.CMessageHello;
import authenticateddh.messageformats.CPacket;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class CInterClientServerCommunicationProtocol {

    private int threadNo;
  
    public CInterClientServerCommunicationProtocol(int threadNo)
    {
        this.threadNo = threadNo;
        System.out.println("CCommunicationProtocol");
    }


    public CCommandType processInput(CPacket packet) {

        System.out.println("Processing server input ... FLAG = " + packet.getFlag());
        CCommandType command = CCommandType.CT_ERROR;

         if(packet.getFlag() == CCommandType.CT_HELLO) {
             command = packet.getFlag();
             processCMessageHello(packet.getCMessage());
        }
        else if(packet.getFlag() == CCommandType.CT_MESSAGE) {
             command = packet.getFlag();
             processCMessageComm(packet.getCMessage());
        }

        else {
             //command = "Error!!";
        }
        
        return command;
    }

    public CPacket processOutput(CCommandType command, String message, int friendID) {

        CPacket packet = new CPacket();

        System.out.println("Processing server output... Command = " + command.toString());

        if(command == CCommandType.CT_HELLO) {
            packet = prepareCMessageHello(command, friendID);
        }
        else if(command == CCommandType.CT_MESSAGE) {
            packet = prepareCMessageComm(command, message);
        }

        else {
            packet.setFlag(CCommandType.CT_ERROR);
        }

        CCurrentCommand.getInstance().clearCurrentCommand();
        return packet;
    }

    /////////////////// KOMUNIKACJA KLIENT - KLIENT OUTPUT/////////////////////////

    private CPacket prepareCMessageComm(CCommandType command, String message) {
        CPacket packet = new CPacket();

        CMessageComm cMessageComm = new CMessageComm(message);

        packet.setFlag(command);
        packet.setCMessage(cMessageComm);

        return packet;
    }

    private CPacket prepareCMessageHello(CCommandType command, int friendID) {
        CPacket packet = new CPacket();

        CFriendUser cFriendUser = CFriendUserManager.getInstance().getUser(friendID);

        CMessageHello cMessageHello = new CMessageHello(CClientConstraints.getInstance().getID(),
                                                        CClientConstraints.getInstance().getR_ID(),
                                                        cFriendUser.getMy_uID_());

        packet.setFlag(command);
        packet.setCMessage(cMessageHello);

        return packet;
    }

    //tylko to istotne
    /////////////////// KOMUNIKACJA KLIENT - KLIENT INPUT /////////////////////////

    private void processCMessageComm(CMessage message) {
        
        String decryptedMessage = ((CMessageComm) message).getEncryptedMessage();
        if(threadNo >= 1000)
            CInterClientConnectorServer.getInstance().setFriendID(threadNo, ((CMessageComm) message).getID());
        try {
            decryptedMessage = CClientConstraints.decryptMessage(CFriendUserManager.getInstance().getUser(threadNo).getConnectionKey_(), decryptedMessage);
            ClientDHApp1.getInstance().processIncomingMessage(threadNo, decryptedMessage);
        } catch (Exception ex) {
            Logger.getLogger(CInterClientServerCommunicationProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }

       
    }

    synchronized private void processCMessageHello(CMessage message) {

        int ID = ((CMessageHello)message).getID();
        BigInteger r_ID = ((CMessageHello)message).getR();
        BigInteger u_ID = ((CMessageHello)message).getU();


        CFriendUser cFriendUser = CFriendUserManager.getInstance().getUser(ID);
        cFriendUser.setConnectionParameters(r_ID, u_ID);
        
        CInterClientConnectorServer.getInstance().setFriendID(threadNo, ID);
        CFriendUserManager.getInstance().computeConnectionKey(ID);
        ClientDHApp1.getInstance().openMessageWindow(ID, cFriendUser.getNickname_());
    }

    public void setThreadNo(int threadNo) {
        this.threadNo = threadNo;
    }
}
