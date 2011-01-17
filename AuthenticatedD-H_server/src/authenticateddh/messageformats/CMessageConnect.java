/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import java.net.InetAddress;

/**
 *
 * @author Sebastian
 */
public class CMessageConnect extends CMessage {

    private int ID;
    private String ipAddress;
    private int portNumber;

    public CMessageConnect(String ipAddress, int portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public CMessageConnect(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPortNumber() {
        return portNumber;

    }



    
}
