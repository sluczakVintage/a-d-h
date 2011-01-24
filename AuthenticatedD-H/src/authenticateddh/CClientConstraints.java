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
    
    /**
     *
     */
    public static final int TCP_PORT = 27500;
    public static final int SERVER_TCP_PORT = 58453;
    /**
     *
     */
    public static final String SERVER_IP = "localhost";
    private static final BigInteger TWO = new BigInteger(String.valueOf(2));
    private static final BigInteger ZERO = new BigInteger(String.valueOf(0));
    private static final BigInteger ONE = new BigInteger(String.valueOf(1));

    private CClientConstraints()
    {
        System.out.println("CClientConstraints");
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return
     */
    public BigInteger getG() {
        return g;
    }

    /**
     *
     * @return
     */
    public BigInteger getQ() {
        return q;
    }

    /**
     *
     * @return
     */
    public BigInteger getR_ID() {
        return r_ID;
    }

    /**
     * 
     * @return
     */
    public int getS_ID() {
        return s_ID;
    }

    /**
     *
     * @return
     */
    public BigInteger getY() {
        return y;
    }
    private BigInteger y;
    private BigInteger q;
    private BigInteger r_ID;
    private int s_ID;

    /**
     *
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @param password
     */
    public void setPasswordHash(String password) {
        this.passwordHash = CMessageDigestFile.getInstance().getSHA256Checksum(password);
        
    }

    /**
     *
     * @return
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     *
     * @return
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     *
     * @param ID
     * @param g
     * @param y
     * @param q
     * @param r_ID
     * @param s_ID
     */
    public void setUserData(int ID, BigInteger g, BigInteger y, BigInteger q, BigInteger r_ID, int s_ID ) {
        this.ID = ID;
        this.g = g;
        this.y = y;
        this.q = q;
        this.r_ID = r_ID;
        this.s_ID = s_ID;
    }

    /**
     *
     * @param a
     * @return
     */
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

    /**
     *
     * @param a
     * @return
     */
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



    /**
     *
     * @param length
     * @return
     */
    synchronized public int getRandomNumber(int length){
            SecureRandom random = new SecureRandom();
            BigInteger temp = new BigInteger(length, random);
            return temp.intValue();
    }

    // w tej metodzie umiescilem rzeczy ktore powinny byc zrobione przed wyslaniem
    // wiadomosc CMessageHello do CFriendUser'a
    // @param ID id usera z ktorym bedziemy sie witac
    /**
     *
     * @param friendID
     */
    public void prepareToHello(int friendID){
        CFriendUser cFriendUser = CFriendUserManager.getInstance().getUser(friendID);

        //te 3 pola (ponizej) wysylamy  podczas wiadomosci CMessageHello
        int IDs = ID;
        BigInteger rID = r_ID;
        BigInteger UID = cFriendUser.generateMyUID_();

    }

    // w tej metodzie umiescilem rzeczy ktore powinny byc zrobione po odebraniu
    // wiadomosci CMessageHello od CFriendUser'a
    // @param tutaj sa rzeczy odebrane w wiadomosci od kolezki
    // @return metoda zwraca klucz ktorym dalej bedziemy szyfrowac
    // w algorytmie szyfrowania symetrycznego kazda wiadomosc
    // UWAGA, ostatnia linijka moze sie wykonywac zajebiscie dlugo
    // nie byla jeszcze testowana ;)
    /**
     *
     * @param ID
     * @param friend_rID
     * @param friend_uID
     * @return
     */
    public BigInteger processHelloData(int ID, BigInteger friend_rID, BigInteger friend_uID){
         CFriendUser cFriendUser = CFriendUserManager.getInstance().getUser(ID);
         cFriendUser.setConnectionParameters(friend_rID, friend_uID);
         return CFriendUserManager.getInstance().computeConnectionKey(ID);
    }
}

