/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CMessage;
import authenticateddh.messageformats.CMessageComm;
import authenticateddh.messageformats.CMessageConnect;
import authenticateddh.messageformats.CMessageHello;
import authenticateddh.messageformats.CMessageLogin;
import authenticateddh.messageformats.CMessageRegister;
import authenticateddh.messageformats.CPacket;
import java.math.BigInteger;

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

    synchronized public boolean processInput(CPacket packet) {

        System.out.println("Processing input ... FLAG = " + packet.getFlag());
        String command = "Error!";

         System.out.println("Processing input... Flag = " + packet.getFlag());

         if(packet.getFlag().equals("Register")) {
             command = packet.getFlag();
             processRegistrationData(packet.getCMessage());
         }
         else if(packet.getFlag().equals("Login")) {
             command = packet.getFlag();
             
        }
        else if(packet.getFlag().equals("Hello")) {
             command =packet.getFlag();
        }
        else if(packet.getFlag().equals("Message")) {
             command =packet.getFlag();
        }
        else if(packet.getFlag().equals("Connect")) {
             command =packet.getFlag();
        }
        else {
             //command = "Error!!";
        }
        return true;
    }

    synchronized public CPacket processOutput(String message) {

        CPacket packet = new CPacket();
        String command = CCurrentCommand.getInstance().getCurrentCommand();

        System.out.println("Processing output... Command = " + command);

        if(command.equals("Register")) {
            packet = prepareCMessageRegister(command);
        }
        else if(command.equals("Login")) {
            packet = prepareCMessageLogin(command);
        }
        else if(command.equals("Hello")) {
            packet = prepareCMessageHello(command);
        }
        else if(command.equals("Message")) {
            packet = prepareCMessageComm(command, message);
        }
        else if(command.equals("Connect")) {
            packet = prepareCMessageConnect(command);
        }
        else {
            packet.setFlag("error");
        }

        CCurrentCommand.getInstance().clearCurrentCommand();
        return packet;
    }

    private CPacket prepareCMessageRegister(String command) {
        CPacket packet = new CPacket();

        CMessageRegister cMessageRegister = new CMessageRegister(CClientConstraints.getInstance().getNickname(), CClientConstraints.getInstance().getPasswordHash());
        packet.setFlag(command);
        packet.setCMessage(cMessageRegister);

        return packet;
    }

    private CPacket prepareCMessageLogin(String command) {
        CPacket packet = new CPacket();

        CMessageLogin cMessageLogin = new CMessageLogin(CClientConstraints.getInstance().getID(), CClientConstraints.getInstance().getNickname(), CClientConstraints.getInstance().getPasswordHash());

        packet.setFlag(command);
        packet.setCMessage(cMessageLogin);

        return packet;
    }

    private CPacket prepareCMessageComm(String command, String message) {
        CPacket packet = new CPacket();

        //@todo -> kodowanie wiadomosci
        CMessageComm cMessageComm = new CMessageComm("tajny kod");

        packet.setFlag(command);
        packet.setCMessage(cMessageComm);

        return packet;
    }

    private CPacket prepareCMessageHello(String command) {
        CPacket packet = new CPacket();

        //@todo -> r i u
        CMessageHello cMessageHello = new CMessageHello(0, new BigInteger("1"), new BigInteger("1"));

        packet.setFlag(command);
        packet.setCMessage(cMessageHello);

        return packet;
    }

    private CPacket prepareCMessageConnect(String command) {
        CPacket packet = new CPacket();

        //@todo -> ID z kim chcemy gadac
        CMessageConnect cMessageConnect = new CMessageConnect(0);

        packet.setFlag(command);
        packet.setCMessage(cMessageConnect);

        return packet;
    }

    private void processRegistrationData(CMessage cMessage) {
        CClientConstraints.getInstance().setUserData(((CMessageRegister)cMessage).getID(),
                ((CMessageRegister)cMessage).getG(),
                ((CMessageRegister)cMessage).getY(),
                ((CMessageRegister)cMessage).getQ(),
                ((CMessageRegister)cMessage).getR_ID(),
                ((CMessageRegister)cMessage).getS_ID());
    }
  
}
