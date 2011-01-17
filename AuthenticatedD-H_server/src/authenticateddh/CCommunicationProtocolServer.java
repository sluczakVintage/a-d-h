/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CCommand;
import authenticateddh.messageformats.CMessage;
import authenticateddh.messageformats.CMessageLogin;
import authenticateddh.messageformats.CMessageRegister;
import authenticateddh.messageformats.CPacket;
import java.math.BigInteger;
import java.net.Socket;

/**
 *
 * @author Sebastian
 */
public class CCommunicationProtocolServer {
            
     public CCommand processInput(CPacket packet, Socket socket) {

         CCommand command = new CCommand("Error!", 0);

         System.out.println("Processing input... Flag = " + packet.getFlag());

         if(packet.getFlag().equals("Register")) {
             command.setCommand(packet.getFlag());
             command.setID(registerUser(packet.getCMessage(), socket));
         }
         else if(packet.getFlag().equals("Login")) {
             command.setCommand(packet.getFlag());            
             command.setID( loginUser(packet.getCMessage(), socket) );
        }
        else if(packet.getFlag().equals("Hello")) {
             command.setCommand(packet.getFlag());
        }
        else if(packet.getFlag().equals("Message")) {
             command.setCommand(packet.getFlag());
        }
        else if(packet.getFlag().equals("Connect")) {
             command.setCommand(packet.getFlag());
        }
        else {
             //command = "Error!!";
        }

        return command;
    }

    public CPacket processOutput(CCommand command) {

        CPacket packet = new CPacket();

        System.out.println("Processing output... Command = " + command.getCommand());

         if(command.equals("Register")) {
             packet = prepareCMessageRegister(command);
         }
         else if(command.equals("Login")) {
             
        }
        else if(command.equals("Hello")) {
             
        }
        else if(command.equals("Message")) {
             
        }
        else if(command.equals("Connect")) {
             
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

    private CPacket prepareCMessageRegister(CCommand command) {
        CPacket packet = new CPacket();

        User user = CUserManager.getInstance().getUser(command.getID());
        
        CMessageRegister cMessageRegister = new CMessageRegister(user.getNickname(), null, user.getID(), user.getG(), user.getY(), KeyGenerationCenter.getInstance().getQ(), user.getR_ID(), user.getS_ID());
        packet.setFlag(command.getCommand());
        packet.setCMessage(cMessageRegister);

        return packet;
    }

  
}
