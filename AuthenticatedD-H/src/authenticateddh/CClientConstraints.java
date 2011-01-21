/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Sebastian
 */
public class CClientConstraints {

    private static CClientConstraints instance;
    
    public static final int TCP_PORT = 25802;
    public static final String SERVER_IP = "localhost";
    private static final BigInteger TWO = new BigInteger(String.valueOf(2));
    private static final BigInteger ZERO = new BigInteger(String.valueOf(0));
    private static final BigInteger ONE = new BigInteger(String.valueOf(1));

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

    static public int H1(BigInteger a) {

        BigInteger result = new BigInteger(String.valueOf(1));
        try {
            byte[] defaultBytes = a.toByteArray();
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();
            int i = 5;
            result = new BigInteger(messageDigest);
            //System.out.println("liczba po wyjsciu z messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        } catch (NoSuchAlgorithmException ex) {
        }
        while (result.bitLength() > 8) {
            result = result.divide(TWO);
        }
        //System.out.println("liczba po wyjsciu z dzielenia w messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        System.out.println("Wynik H1 to " + result);
        if (result.compareTo(ZERO)==-1) result = result.negate();
        System.out.println("Po zmianie znaku wynik H1 to " + result);
        return result.intValue();
    }

    static public BigInteger H2(BigInteger a) {

        BigInteger result = new BigInteger(String.valueOf(1));
        try {
            byte[] defaultBytes = a.toByteArray();
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();
            int i = 5;
            result = new BigInteger(messageDigest);
            //System.out.println("liczba po wyjsciu z messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(KeyGenerationCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("liczba po wyjsciu z dzielenia w messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        if (result.compareTo(ZERO) == -1) {
            result = result.negate();
        }
        return result;
    }



    synchronized public int getRandomNumber(int length){
            SecureRandom random = new SecureRandom();
            BigInteger temp = new BigInteger(length, random);
            return temp.intValue();
    }
}

