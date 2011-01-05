/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package authenticateddh_server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author malina
 */
public class KeyGenerationCenter {

    private int groupLength_;
    private BigInteger primeOrder_;
    private BigInteger generator_;
    private int x_;
    private BigInteger y_;
    private static final BigInteger TWO = new BigInteger(String.valueOf(2));
    private static final BigInteger ONE = new BigInteger(String.valueOf(1));
    private static final BigInteger ZERO = new BigInteger(String.valueOf(0));

    /* Konstruktor domyslny */
    private KeyGenerationCenter() {
        groupLength_ = 128;
        inititializeKGC();
        // na razie nie ma tu nic sensownego
    }

    private boolean inititializeKGC() {

        GeneratorFactory fact = new GeneratorFactory(96);
        primeOrder_ = fact.getP();
        if(primeOrder_.bitLength()!=groupLength_) {
            System.out.println("nie jest dobrze, dlugosc primeorder jest za mala i zamiast " + groupLength_ + " wynosi " + primeOrder_.bitLength());
            return false;
        }
        generator_ = fact.getG();
        x_=getRandomNumber(16);
        y_=generator_.pow(x_);
        //System.out.println("grupa to " + primeOrder_ + ",jej dlugosc to " + primeOrder_.bitLength() + ", natomiast generator to " + generator_ + ",a jej dlugosc to " + generator_.bitLength());

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
            //System.out.println();
            int i=5;
            result = new BigInteger(messageDigest);
            System.out.println("liczba po wyjsciu z messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
            //System.out.printf("p = %x (%d bits)\n", result, result.bitLength());
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(KeyGenerationCenter.class.getName()).log(Level.SEVERE, null, ex);
        }
        //result.bitLength();
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
            //System.out.println();
            int i=5;
            result = new BigInteger(messageDigest);
            System.out.println("liczba po wyjsciu z messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
            //System.out.printf("p = %x (%d bits)\n", result, result.bitLength());
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(KeyGenerationCenter.class.getName()).log(Level.SEVERE, null, ex);
        }


        //result.bitLength();
        //while(result.bitLength()>16){
            //result =result.divide(TWO);
        //}
        System.out.println("liczba po wyjsciu z dzielenia w messageDigest to " + result + ", natomiast jej dlugosc to " + result.bitLength());
        if (result.compareTo(ZERO)==-1) result.negate();
        return result;

    }

        static private int getRandomNumber(int length){
            SecureRandom random = new SecureRandom();
            BigInteger temp = new BigInteger(length, random);
            return temp.intValue();
        }

    public static void main(String[] args) {
        System.out.println("Testujemy dzialanie tego syfu");
        KeyGenerationCenter KGC = new KeyGenerationCenter();
        //double gener = 4;
       //double order = 7;
        SecureRandom random = new SecureRandom();
        BigInteger a = new BigInteger(1038283, random);
        H1(a);
        H1(a);
        //if (isIntGenerator(gener, order)) System.out.println(gener +" jest generatorem dla order rownego " + order);
        //else System.out.println(gener +" nie jest generatorem dla order rownego " + order);
    }
}
