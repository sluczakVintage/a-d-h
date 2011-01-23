/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import authenticateddh.CLoggedList;
import java.math.BigInteger;

/**
 *
 * @author Sebastian
 */
public class CMessageRegister extends CMessage {
    private String nickname;
    private String passwordHash;
    private int ID;
    private BigInteger g;
    private BigInteger y;
    private BigInteger q;
    private BigInteger r_ID;
    private int s_ID;
    private CLoggedList loggedList;


    

    public CMessageRegister(String nickname, String passwordHash, int ID, BigInteger g, BigInteger y, BigInteger q, BigInteger r_ID, int s_ID) {
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.ID = ID;
        this.g = g;
        this.y = y;
        this.q = q;
        this.r_ID = r_ID;
        this.s_ID = s_ID;
    }

    public CMessageRegister(String nickname, String passwordHash) {
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.ID = 0;
        this.g = null;
        this.y = null;
        this.q = null;
        this.r_ID = null;
        this.s_ID = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getID() {
        return ID;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getR_ID() {
        return r_ID;
    }

    public int getS_ID() {
        return s_ID;
    }

    public void setUserList(CLoggedList cLoggedList) {
        this.loggedList = cLoggedList;
    }

    public CLoggedList getUserList() {
        return loggedList;
    }

}
