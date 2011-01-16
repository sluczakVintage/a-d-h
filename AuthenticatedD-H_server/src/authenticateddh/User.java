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
 * @author malina
 */
public class User {

    private static final BigInteger TWO = new BigInteger(String.valueOf(2));
    private static final BigInteger ZERO = new BigInteger(String.valueOf(0));
    private static final BigInteger ONE = new BigInteger(String.valueOf(1));
    int ID_;
    BigInteger rID_;
    int sID_;
    BigInteger generator_;
    BigInteger y_;

    //konstruktor domyslny
    User() {
    }

    User(int id, BigInteger g, BigInteger y){
        ID_=id;
        y_=y;
        generator_=g;
        System.out.println("Utworzono nowego usera ");
    }

    boolean checkKey(KeyPair kp) {

        int sID = kp.getsID_();
        System.out.println("1");
        BigInteger rID = kp.getrID_();
        System.out.println("2");
        BigInteger left = generator_.pow(sID);
        System.out.println("3");
        BigInteger right = rID.multiply(y_.pow(H1(rID.add(BigInteger.valueOf(ID_)))));
        System.out.println("4");
        if (left.equals(right)) return true;
        else return false;
    }

    static private int H1(BigInteger a) {

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

    static private BigInteger H2(BigInteger a) {

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

    static private int getRandomNumber(int length) {
        SecureRandom random = new SecureRandom();
        BigInteger temp = new BigInteger(length, random);
        return temp.intValue();
    }
}
