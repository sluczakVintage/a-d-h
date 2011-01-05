/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package authenticateddh_server;

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
    int ID;
    BigInteger rID;
    int sID;

    User() {
    }

    boolean checkKey(){
        
        return true;
    }

        static private int H1(BigInteger a) {

        BigInteger result= new BigInteger(String.valueOf(1));
        try {
            byte[] defaultBytes = a.toByteArray();
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();
            int i=5;
            result = new BigInteger(messageDigest);
            System.out.println("liczba po wyjsciu z messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        } catch (NoSuchAlgorithmException ex) {
        }
        while(result.bitLength()>16){
            result =result.divide(TWO);
        }
        System.out.println("liczba po wyjsciu z dzielenia w messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        if (result.compareTo(ZERO)==-1) result.negate();
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
            int i=5;
            result = new BigInteger(messageDigest);
            System.out.println("liczba po wyjsciu z messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(KeyGenerationCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("liczba po wyjsciu z dzielenia w messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        if (result.compareTo(ZERO)==-1) result.negate();
        return result;
    }
}
