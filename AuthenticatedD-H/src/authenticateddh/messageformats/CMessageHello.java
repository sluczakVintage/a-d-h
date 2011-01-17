/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import java.math.BigInteger;

/**
 *
 * @author Sebastian
 */
public class CMessageHello extends CMessage {

    private int ID;
    private BigInteger r;
    private BigInteger u;

    public CMessageHello(int ID, BigInteger r, BigInteger u) {
        this.ID = ID;
        this.r = r;
        this.u = u;
    }

    

    public int getID() {
        return ID;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getU() {
        return u;
    }

}
