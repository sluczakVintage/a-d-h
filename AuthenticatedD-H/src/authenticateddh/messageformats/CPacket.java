/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import java.io.Serializable;

/**
 *
 * @author Sebastian
 */
public class CPacket implements Serializable {

    private String flag;
    private CMessage message;
    
    public CPacket() {

    }

    public void setFlag(String flag_) {
        flag = flag_;
    }

    public void setCMessage(CMessage message_) {
        message = message_;
    }


    public String getFlag() {
        return this.flag;
    }

    public CMessage getCMessage() {
        return this.message;
    }


}
