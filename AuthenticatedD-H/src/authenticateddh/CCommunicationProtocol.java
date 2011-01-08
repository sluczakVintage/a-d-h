/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

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

        //CHash == packet.CHash
        return true;
        //else
        //return false
    }

    public CPacket processOutput(String command) {

        CPacket packet = new CPacket();

        System.out.println("Processing output... Command = " + command);
        
        if(command.equals("test")) {
                    packet.setFlag(command);
                }
                else {
                    packet.setFlag("no reason");
                }

        return packet;
    }
  
}
