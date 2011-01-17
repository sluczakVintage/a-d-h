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
public class CCommand {
    private String command;
    private int ID;
    private InetAddress inetAddress;

    public CCommand(String command, int ID) {
        this.command = command;
        this.ID = ID;
    }

    public boolean equals(String string) {
        return string.equals(command);
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
}
