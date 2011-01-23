/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CMessageComm;
import authenticateddh.messageformats.CMessageHello;
import authenticateddh.messageformats.CPacket;
import java.math.BigInteger;

/**
 *
 * @author Sebastian
 */
public class CClientCommunicationProtocol {

      private static CClientCommunicationProtocol instance;

    private CClientCommunicationProtocol()
    {
        System.out.println("CCommunicationProtocol");
    }

    public static synchronized CClientCommunicationProtocol getInstance() {
	if (instance == null) {
		instance = new CClientCommunicationProtocol();
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

         if(packet.getFlag() == CCommandType.CT_HELLO) {
             command = packet.getFlag();
        }
        else if(packet.getFlag() == CCommandType.CT_MESSAGE) {
             command = packet.getFlag();
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

        if(CCurrentCommand.getInstance().getCurrentCommand() == CCommandType.CT_HELLO) {
            packet = prepareCMessageHello(command);
        }
        else if(CCurrentCommand.getInstance().getCurrentCommand() == CCommandType.CT_MESSAGE) {
            packet = prepareCMessageComm(command, message);
        }

        else {
            packet.setFlag(CCommandType.CT_ERROR);
        }

        CCurrentCommand.getInstance().clearCurrentCommand();
        return packet;
    }

    /////////////////// KOMUNIKACJA KLIENT - KLIENT /////////////////////////

    private CPacket prepareCMessageComm(CCommandType command, String message) {
        CPacket packet = new CPacket();

        //@todo -> kodowanie wiadomosci
        CMessageComm cMessageComm = new CMessageComm("tajny kod");

        packet.setFlag(command);
        packet.setCMessage(cMessageComm);

        return packet;
    }

    private CPacket prepareCMessageHello(CCommandType command) {
        CPacket packet = new CPacket();

        //@todo -> r i u
        CMessageHello cMessageHello = new CMessageHello(0, new BigInteger("1"), new BigInteger("1"));

        packet.setFlag(command);
        packet.setCMessage(cMessageHello);

        return packet;
    }

    /////////////////// KOMUNIKACJA KLIENT - KLIENT /////////////////////////

    private CPacket processCMessageComm(CCommandType command, String message) {
        CPacket packet = new CPacket();

        //@todo -> kodowanie wiadomosci
        CMessageComm cMessageComm = new CMessageComm("tajny kod");

        packet.setFlag(command);
        packet.setCMessage(cMessageComm);

        return packet;
    }

    private CPacket processCMessageHello(CCommandType command) {
        CPacket packet = new CPacket();

        //@todo -> r i u
        CMessageHello cMessageHello = new CMessageHello(0, new BigInteger("1"), new BigInteger("1"));

        packet.setFlag(command);
        packet.setCMessage(cMessageHello);

        return packet;
    }



}
