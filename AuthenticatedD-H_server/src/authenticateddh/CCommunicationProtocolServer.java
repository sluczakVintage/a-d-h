/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

/**
 *
 * @author Sebastian
 */
public class CCommunicationProtocolServer {
            
     public String processInput(CPacket packet) {

         String command;
         System.out.println("Processing input... Flag = " + packet.getFlag());
         if(packet.getFlag().equals("test")) {
                command = "test passed";
            }
            else
                command = "no reason!!";

        //CHash == packet.CHash
        return command;
        //else
        //return false
    }

    public CPacket processOutput(String command) {

        CPacket packet = new CPacket();

        System.out.println("Processing output... Command = " + command);

        if(command.equals("test passed")) {
                    packet.setFlag(command);
                }
                else {
                    packet.setFlag("no reason!!");
                }

        return packet;
    }

  
}
