/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import authenticateddh.CCommandType;
import java.io.Serializable;

/**
 *
 * @author Sebastian
 */
public class CPacket implements Serializable {

    //private String flag;
    private CCommandType flag;
    private CMessage message;
    
    public CPacket() {

    }

    public void setFlag(CCommandType flag_) {
        flag = flag_;
    }

    public void setCMessage(CMessage message_) {
        message = message_;
    }


    public CCommandType getFlag() {
        return this.flag;
    }

    public CMessage getCMessage() {
        return this.message;
    }


}
