/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh_server;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author malina
 */
public class KeyGenerationCenter {

    private int groupLength_;
    private BigInteger primeOrder_;
    private BigInteger generator_;
    private BigInteger x_;
    private BigInteger y_;
    private static final BigInteger ONE = new BigInteger(String.valueOf(1));
    private static final BigInteger ZERO = new BigInteger(String.valueOf(0));

    /* Konstruktor domyslny */
    private KeyGenerationCenter(){
        groupLength_=128;
        inititializeKGC();
        // na razie nie ma tu nic sensownego
    }

    private boolean inititializeKGC(){
        SecureRandom random = new SecureRandom();
        primeOrder_ = BigInteger.probablePrime(groupLength_, random);

        generator_=getRandomGenerator();
        System.out.println("grupa to " + primeOrder_ + ", natomiast generator to " + generator_);
        return false;

    }


    private BigInteger getRandomGenerator(){
        SecureRandom random = new SecureRandom();
        BigInteger tempGenerator;
        do{
            tempGenerator = new BigInteger(groupLength_, random);
            System.out.println("dzialam");
        }while (!numberIsGenerator(tempGenerator, primeOrder_) || (tempGenerator.compareTo(primeOrder_) > -1) || (tempGenerator.equals(ZERO)) );
        return tempGenerator;
    }

    private boolean numberIsGenerator(BigInteger number, BigInteger order){
        BigInteger gcd = number.gcd(order);
        if (gcd.equals(ONE) && !number.equals(ONE)) return true;
        else return false;
    }


     public static void main(String[] args) {
     System.out.println("Testujemy dzialanie tego syfu");
     KeyGenerationCenter KGC = new KeyGenerationCenter();
     }
}
