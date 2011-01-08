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
    private CHash hash;

    public CPacket() {

    }

    void setFlag(String flag_) {
        flag = flag_;
    }

    void setCMessage(CMessage message_) {
        message = message_;
    }

    void setCHash(CHash hash_) {
        hash = hash_;
    }

    String getFlag() {
        return this.flag;
    }

    CMessage getCMessage() {
        return this.message;
    }

    CHash getCHash() {
        return this.hash;
    }

}
