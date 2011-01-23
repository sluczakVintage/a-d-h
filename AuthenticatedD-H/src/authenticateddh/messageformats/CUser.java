/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.InetAddress;

/**
 *
 * @author Sebastian
 */
public class CUser implements Serializable{


    private String nickname_;
    private int ID_;

    private InetAddress inetAddress;
    private boolean available_;



    CUser(){}


    public CUser(int ID, String nickname, InetAddress inetAddress, boolean available) {
        this.nickname_ = nickname;
        this.ID_ = ID;
        this.inetAddress = inetAddress;
        this.available_ = available;
    }


    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void changeAvailability(boolean available){
        available_=available;
    }

    public int getID_() {
        return ID_;
    }

    public boolean getAvailable() {
        return available_;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getNickname_() {
        return nickname_;
    }
}
