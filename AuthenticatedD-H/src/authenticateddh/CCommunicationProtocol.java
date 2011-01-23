
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CMessage;
import authenticateddh.messageformats.CMessageLogin;
import authenticateddh.messageformats.CMessageRegister;
import authenticateddh.messageformats.CMessageUserList;
import authenticateddh.messageformats.CPacket;

/**
 *
 * @author Sebastian
 */


public class CCommunicationProtocol {

      private static CCommunicationProtocol instance;

    private CCommunicationProtocol()
    {
        System.out.println("CCommunicationProtocol");
    }

    public static synchronized CCommunicationProtocol getInstance() {
	if (instance == null) {
		instance = new CCommunicationProtocol();
	}
	return instance;
    }


    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    public boolean processInput(CPacket packet) {

        System.out.println("Processing input ... FLAG = " + packet.getFlag());
        CCommandType command = CCommandType.CT_ERROR;

         System.out.println("Processing input... Flag = " + packet.getFlag());

         if(packet.getFlag() == CCommandType.CT_REGISTER) {
             command = packet.getFlag();
             processRegistrationData(packet.getCMessage());
         }
         else if(packet.getFlag() == CCommandType.CT_LOGIN) {
             command = packet.getFlag();
             processLoginData(packet.getCMessage());
        }
        else if(packet.getFlag() == CCommandType.CT_LIST) {
             command = packet.getFlag();
             processUserList(packet.getCMessage());
        }

        else {
             //command = "Error!!";
        }
        return true;
    }

    public CPacket processOutput(String message) {

        CPacket packet = new CPacket();
        CCommandType command = CCurrentCommand.getInstance().getCurrentCommand();

        System.out.println("Processing output... Command = " + command.toString());

        if(CCurrentCommand.getInstance().getCurrentCommand() == CCommandType.CT_REGISTER) {
            packet = prepareCMessageRegister(command);
        }
        else if(CCurrentCommand.getInstance().getCurrentCommand() == CCommandType.CT_LOGIN) {
            packet = prepareCMessageLogin(command);
        }
        else if(CCurrentCommand.getInstance().getCurrentCommand() == CCommandType.CT_LIST) {
            packet = prepareCMessageUserList(command);
        }
        else {
            packet.setFlag(CCommandType.CT_ERROR);
        }

        CCurrentCommand.getInstance().clearCurrentCommand();
        return packet;
    }

/////////////////// KOMUNIKACJA KLIENT - SERWER /////////////////////////

    //////////////output preparing
    private CPacket prepareCMessageRegister(CCommandType command) {
        CPacket packet = new CPacket();

        CMessageRegister cMessageRegister = new CMessageRegister(CClientConstraints.getInstance().getNickname(), CClientConstraints.getInstance().getPasswordHash());
        packet.setFlag(command);
        packet.setCMessage(cMessageRegister);

        return packet;
    }

    private CPacket prepareCMessageLogin(CCommandType command) {
        CPacket packet = new CPacket();

        CMessageLogin cMessageLogin = new CMessageLogin(CClientConstraints.getInstance().getID(), CClientConstraints.getInstance().getNickname(), CClientConstraints.getInstance().getPasswordHash());

        packet.setFlag(command);
        packet.setCMessage(cMessageLogin);

        return packet;
    }

    private CPacket prepareCMessageUserList(CCommandType command) {
        CPacket packet = new CPacket();


        CMessageUserList cMessageUserList = new CMessageUserList(CClientConstraints.getInstance().getID(), CClientConstraints.getInstance().getNickname());

        packet.setFlag(command);
        packet.setCMessage(cMessageUserList);

        return packet;
    }


    //////////////// input processing

    private void processRegistrationData(CMessage cMessage) {
        CClientConstraints.getInstance().setUserData(((CMessageRegister)cMessage).getID(),
                ((CMessageRegister)cMessage).getG(),
                ((CMessageRegister)cMessage).getY(),
                ((CMessageRegister)cMessage).getQ(),
                ((CMessageRegister)cMessage).getR_ID(),
                ((CMessageRegister)cMessage).getS_ID());
        System.out.println("Udalo sie odebrać moje dane, ich długości w bitach to (oprócz S_ID): G " + CClientConstraints.getInstance().getG().bitLength() + " Y "  + CClientConstraints.getInstance().getY().bitLength() + " Q "  + CClientConstraints.getInstance().getQ().bitLength() + " S_ID "  + CClientConstraints.getInstance().getS_ID() + " R_ID "  + CClientConstraints.getInstance().getR_ID().bitLength());
        CFriendUserManager.getInstance().resetCFriendUserMap( ((CMessageRegister)cMessage).getUserList());

    }

    private void processLoginData(CMessage cMessage) {
        CClientConstraints.getInstance().setID(((CMessageLogin)cMessage).getID());
        System.out.println("Moje nowe ID to: " + CClientConstraints.getInstance().getID());
        CFriendUserManager.getInstance().resetCFriendUserMap( ((CMessageLogin)cMessage).getUserList());
    }

    private void processUserList(CMessage cMessage) {
        CFriendUserManager.getInstance().resetCFriendUserMap( ((CMessageUserList)cMessage).getUserList());
        System.out.println("Moje nowe ID to: " + CClientConstraints.getInstance().getID());
    }






}
