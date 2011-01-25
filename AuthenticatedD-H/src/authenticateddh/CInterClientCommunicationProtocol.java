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
public class CInterClientCommunicationProtocol {

    private int threadNo;
  
    public CInterClientCommunicationProtocol(int threadNo)
    {
        this.threadNo = threadNo;
        System.out.println("CCommunicationProtocol");
    }


    public CCommandType processInput(CPacket packet) {

        System.out.println("Processing input ... FLAG = " + packet.getFlag());
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

        System.out.println("Processing output... Command = " + command.toString());

        if(command == CCommandType.CT_HELLO) {
            packet = prepareCMessageHello(command, friendID);
        }
        else if(command == CCommandType.CT_MESSAGE) {
            packet = prepareCMessageComm(command, message);
        }

        else {
            packet.setFlag(CCommandType.CT_ERROR);
        }

        return packet;
    }

    /////////////////// KOMUNIKACJA KLIENT - KLIENT OUTPUT/////////////////////////
    //tylko to istotne
    private CPacket prepareCMessageComm(CCommandType command, String message) {
        CPacket packet = new CPacket();

        try {
            message = CClientConstraints.encryptMessage(CFriendUserManager.getInstance().getUser(threadNo).getConnectionKey_(), message);
            CMessageComm cMessageComm = new CMessageComm(message);
            packet.setFlag(command);
            packet.setCMessage(cMessageComm);
        } catch (Exception ex) {
            Logger.getLogger(CInterClientCommunicationProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return packet;
    }

    private CPacket prepareCMessageHello(CCommandType command, int friendID) {
        CPacket packet = new CPacket();

        CFriendUser cFriendUser = CFriendUserManager.getInstance().getUser(friendID);

        CMessageHello cMessageHello = new CMessageHello(CClientConstraints.getInstance().getID(),
                                                        CClientConstraints.getInstance().getR_ID(),
                                                        cFriendUser.generateMyUID_());

        packet.setFlag(command);
        packet.setCMessage(cMessageHello);

        return packet;
    }

    /////////////////// KOMUNIKACJA KLIENT - KLIENT INPUT /////////////////////////

    private void processCMessageComm(CMessage message) {
        
        String encryptedMessage = ((CMessageComm)message).getEncryptedMessage();
        ClientDHApp1.getInstance().processIncomingMessage(threadNo, encryptedMessage);
        
    }

    private void processCMessageHello(CMessage message) {

        int ID = ((CMessageHello)message).getID();
        BigInteger r_ID = ((CMessageHello)message).getR();
        BigInteger u_ID = ((CMessageHello)message).getU();

         CFriendUser cFriendUser = CFriendUserManager.getInstance().getUser(ID);
         cFriendUser.setConnectionParameters(r_ID, u_ID);
         //CInterClientConnector.getInstance().setFriendID(threadNo, ID);
         CFriendUserManager.getInstance().computeConnectionKey(ID);

         ClientDHApp1.getInstance().openMessageWindow(ID, cFriendUser.getNickname_());
    
    }



}
