/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.math.BigInteger;

/**
 *
 * @author Sebastian
 */
public class CClientConstraints {

    private static CClientConstraints instance;
    
    public static final int TCP_PORT = 25802;
    public static final String SERVER_IP = "localhost";

    private CClientConstraints()
    {
        System.out.println("CClientConstraints");
    }

    public static synchronized CClientConstraints getInstance() {
	if (instance == null) {
		instance = new CClientConstraints();
	}
	return instance;
    }
    
    private int ID = 0;
    private String nickname = "nick";
    private String passwordHash = "haslo";

    private BigInteger g;

    public BigInteger getG() {
        return g;
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

    public BigInteger getY() {
        return y;
    }
    private BigInteger y;
    private BigInteger q;
    private BigInteger r_ID;
    private int s_ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = CMessageDigestFile.getInstance().getSHA256Checksum(password);
        
    }

    public int getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setUserData(int ID, BigInteger g, BigInteger y, BigInteger q, BigInteger r_ID, int s_ID ) {
        this.ID = ID;
        this.g = g;
        this.y = y;
        this.q = q;
        this.r_ID = r_ID;
        this.s_ID = s_ID;
    }

}

