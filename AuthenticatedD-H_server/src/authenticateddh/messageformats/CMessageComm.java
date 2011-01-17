/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

/**
 *
 * @author Sebastian
 */
public class CMessageComm extends CMessage {

    String encryptedMessage;

    public CMessageComm(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }
}
