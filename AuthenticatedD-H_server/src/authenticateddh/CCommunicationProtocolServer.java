/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CCommand;
import authenticateddh.messageformats.CMessage;
import authenticateddh.messageformats.CMessageLogin;
import authenticateddh.messageformats.CMessageRegister;
import authenticateddh.messageformats.CMessageUserList;
import authenticateddh.messageformats.CPacket;
import java.math.BigInteger;
import java.net.Socket;

/**
 *
 * @author Sebastian
 */
public class CCommunicationProtocolServer {
            
     synchronized public CCommand processInput(CPacket packet, Socket socket) {

         CCommand command = new CCommand(CCommandType.CT_ERROR, 0);

         System.out.println("Processing input... Flag = " + packet.getFlag());
         ServerDHApp.getInstance().addLog("Processing input... Flag = " + packet.getFlag());

         if(packet.getFlag() == CCommandType.CT_REGISTER) {
             command.setCommand(packet.getFlag());
             command.setID(registerUser(packet.getCMessage(), socket));
         }
         else if(packet.getFlag() == CCommandType.CT_LOGIN) {
             command.setCommand(packet.getFlag());            
             command.setID( loginUser(packet.getCMessage(), socket) );
        }
        else if(packet.getFlag() == CCommandType.CT_LIST) {
             command.setCommand(packet.getFlag());
             command.setID( ((CMessageUserList)(packet.getCMessage())).getID() );
        }
        else
        {
             //command = "Error!!";
        }

        return command;
    }

    synchronized public CPacket processOutput(CCommand command) {

        CPacket packet = new CPacket();

        System.out.println("Processing output... Command = " + command.getCommand().toString());
        ServerDHApp.getInstance().addLog("Processing output... Command = " + command.getCommand().toString());

         if(command.getCommand() == CCommandType.CT_REGISTER) {
            packet = prepareCMessageRegister(command);
         }
         else if(command.getCommand() == CCommandType.CT_LOGIN) {
            packet = prepareCMessageLogin(command);
        }
        else if(command.getCommand() == CCommandType.CT_LIST) {
            packet = prepareCLoggedList(command);
        }
        else {
             
        }

        return packet;
    }

    private int registerUser(CMessage cMessage, Socket socket) {
        return CUserManager.getInstance().addUser(((CMessageRegister)cMessage).getNickname(), ((CMessageRegister)cMessage).getPasswordHash(), socket.getInetAddress());        
    }

    private int  loginUser(CMessage cMessage, Socket socket) {
        return CUserManager.getInstance().setUserAvailable( ((CMessageLogin)cMessage).getID(), ((CMessageLogin)cMessage).getLogin(), ((CMessageLogin)cMessage).getPasswordHash(), socket.getInetAddress(), true);
    }

    

    //prepare output
    //prepare register data out
    private CPacket prepareCMessageRegister(CCommand command) {
        CPacket packet = new CPacket();
        CMessageRegister cMessageRegister;
        User user = CUserManager.getInstance().getUser(command.getID());
        
        if(user != null) {
            cMessageRegister = new CMessageRegister(user.getNickname(), null, user.getID(), user.getG(), user.getY(), KeyGenerationCenter.getInstance().getQ(), user.getR_ID(), user.getS_ID());
            cMessageRegister.setUserList(CUserManager.getInstance().getLoggedList(command.getID()));
        }
        else {
            cMessageRegister = new CMessageRegister("", null, 0, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, 0);
            cMessageRegister.setUserList(CUserManager.getInstance().getLoggedList(command.getID()));
        }
        packet.setFlag(command.getCommand());
        packet.setCMessage(cMessageRegister);

        return packet;
    }

    private CPacket prepareCMessageLogin(CCommand command) {
        CPacket packet = new CPacket();

        User user = CUserManager.getInstance().getUser(command.getID());

        CMessageLogin cMessageLogin = new CMessageLogin(user.getID(), user.getNickname(), null);
        //lista uzytkownikow
        cMessageLogin.setLoggedList(CUserManager.getInstance().getLoggedList(command.getID()));
        //lista uzytkownikow
        packet.setFlag(command.getCommand());
        packet.setCMessage(cMessageLogin);

        return packet;
    }

    private CPacket prepareCLoggedList(CCommand command) {
        CPacket packet = new CPacket();
//lista uzytkownikow
        CMessageUserList cMessageUserList = new CMessageUserList(CUserManager.getInstance().getLoggedList(command.getID()));
        
//lista uzytkownikow
        packet.setFlag(command.getCommand());
        packet.setCMessage(cMessageUserList);

        return packet;
    }

  
}
