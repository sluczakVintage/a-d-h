/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import authenticateddh.CCommandType;
import java.net.InetAddress;

/**
 *
 * @author Sebastian
 */
public class CCommand {
    private CCommandType command_;
    private int ID;
    private InetAddress inetAddress;

    public CCommand(CCommandType command, int ID) {
        this.command_ = command;
        this.ID = ID;
    }

//    public boolean equals(String string) {
//        return string.equals(command);
//    }

     /**
     *
     * @param command
     * @return
     */
    public boolean equals(CCommandType command_) {
        return this.command_ == command_;
    }
    /**
     *
     * @param command
     */
    public void setCommand(CCommandType command_) {
        this.command_ = command_;
    }

    /**
     *
     * @return
     */
    public CCommandType getCommand() {
        return command_;
    }
    
//    public void setCommand(String command) {
//        this.command = command;
//    }

//    public String getCommand() {
//        return command;
//    }

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
