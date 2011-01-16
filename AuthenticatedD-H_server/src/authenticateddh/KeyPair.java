/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.math.BigInteger;

/**
 *
 * @author malina
 */
public class KeyPair {

    int sID_;
    BigInteger rID_;

    public KeyPair(int sID_, BigInteger rID_) {
        this.sID_ = sID_;
        this.rID_ = rID_;
    }

    public BigInteger getrID_() {
        return rID_;
    }

    public void setrID_(BigInteger rID_) {
        this.rID_ = rID_;
    }

    public int getsID_() {
        return sID_;
    }

    public void setsID_(int sID_) {
        this.sID_ = sID_;
    }

}
