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
    int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public CMessageComm(String encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }
}
