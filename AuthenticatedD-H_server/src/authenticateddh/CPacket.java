/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

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

    void setFlag(String flag_) {
        flag = flag_;
    }

    void setCMessage(CMessage message_) {
        message = message_;
    }


    String getFlag() {
        return this.flag;
    }

    CMessage getCMessage() {
        return this.message;
    }


}
