/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package authenticateddh;

import java.math.BigInteger;
import java.net.InetAddress;

/**
 *
 * @author malina
 */
public class CFriendUser {

    private String nickname_;
    private int ID_;
    private BigInteger rID_;
    private BigInteger uID_;
    private InetAddress inetAddress;
    private boolean available_;
    private BigInteger connectionKey_;

    public void setConnectionKey_(BigInteger connectionKey_) {
        this.connectionKey_ = connectionKey_;
    }

    private int tID_;
    private BigInteger my_uID_;
    boolean generatedKeys_;

    CFriendUser(){}

    public CFriendUser(int ID_, String nickname_, InetAddress inetAddress, boolean available_) {
        this.nickname_ = nickname_;
        this.ID_ = ID_;
        this.inetAddress = inetAddress;
        this.available_ = available_;
        generatedKeys_=false;
    }


    public BigInteger generateMyUID_(){
        if(!generatedKeys_){
        tID_ = CClientConstraints.getInstance().getRandomNumber(4);
        my_uID_= CClientConstraints.getInstance().getG().pow(tID_);
        System.out.println("Jestem " + CClientConstraints.getInstance().getNickname() +  "Dla usera o nickname " + nickname_ + "wygenerowalem my_tID: " + tID_ + " oraz uID: " + my_uID_);
        generatedKeys_=true;
        }
        return my_uID_;
    }


    public void setConnectionParameters(BigInteger rID, BigInteger uID){
        this.rID_=rID;
        this.uID_=uID;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public void changeAvailability(boolean available){
        available_=available;
    }

    public BigInteger getConnectionKey_() {
        return connectionKey_;
    }

    public BigInteger getMy_uID_() {
        return my_uID_;
    }

    public int getTID_() {
        return tID_;
    }

    public int getID_() {
        return ID_;
    }

    public boolean isAvailable_() {
        return available_;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getNickname_() {
        return nickname_;
    }

    public BigInteger getRID_() {
        return rID_;
    }

    public BigInteger getUID_() {
        return uID_;
    }

}
