/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CUser;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author malina
 */
public class CFriendUserManager {

    TreeMap<Integer, CFriendUser> cFriendUserMap;

    private static CFriendUserManager instance;

    public static synchronized CFriendUserManager getInstance() {
	if (instance == null) {
		instance = new CFriendUserManager();
	}
	return instance;
    }

        private CFriendUserManager()
    {
        cFriendUserMap = new TreeMap();
        System.out.println("CFriendUserManager");
    }

    public int addUser(int ID, String nickname, InetAddress inetAddress, boolean available) {
        //KeyPair userKeyPairs = KeyGenerationCenter.getInstance().generateKeys(ID);
        cFriendUserMap.put(ID, new CFriendUser(ID, nickname, inetAddress, available));
        cFriendUserMap.get(ID).generateMyUID_();
        return ID;
    }

        public int setFriendUserAvailable(int ID, InetAddress inetAddress, boolean available){
            CFriendUser cFriendUser = cFriendUserMap.get(ID);
            cFriendUser.changeAvailability(available);
            cFriendUser.setInetAddress(inetAddress);
            cFriendUserMap.put(ID, cFriendUser);
            return ID;
        }

        public int setFriendConnectionData(int ID, BigInteger rID, BigInteger sID){
            CFriendUser cFriendUser = cFriendUserMap.get(ID);
            cFriendUser.setConnectionParameters(rID, sID);
            cFriendUserMap.put(ID, cFriendUser);
            return ID;
        }

    public void resetCFriendUserMap(CLoggedList loggedList) {
        //cFriendUserMap.clear();
        ClientDHApp1.getInstance().cleanupUserList();
        //System.out.println(loggedList.size());

        for(int i = 0; i < loggedList.size() ; i++) {
            CUser tempUser = loggedList.get(i);
            int ID = tempUser.getID_();
            if(!cFriendUserMap.containsKey(ID)){
            addUser(tempUser.getID_(), tempUser.getNickname_(), tempUser.getInetAddress(), tempUser.getAvailable());
            }
        }

        Collection c = cFriendUserMap.values();
        Iterator itr = c.iterator();

        while(itr.hasNext()) {
            CFriendUser tempUser = ((CFriendUser)(itr.next()));
            if(tempUser.isAvailable_())
                ClientDHApp1.getInstance().addUserToList(tempUser.getID_(), tempUser.getNickname_());
        }
        
    }
    public CFriendUser getUser(int ID) {
        return cFriendUserMap.get(ID);
    }

    synchronized BigInteger computeConnectionKey(int ID){

        CFriendUser cFriendUser = cFriendUserMap.get(ID);
        BigInteger rID = cFriendUser.getRID_();
        BigInteger uID = cFriendUser.getUID_();
        int my_tID =  cFriendUser.getTID_();
        int my_sID = CClientConstraints.getInstance().getS_ID();
        BigInteger y = CClientConstraints.getInstance().getY();
        //System.out.println("Counting H1");
        int h1 = CClientConstraints.H1(rID.add(BigInteger.valueOf(ID)));
        //System.out.println("Counting a: uID*rID");
        //BigInteger z1 = uID.multiply(rID.multiply(y)).pow(h1+my_tID+my_sID);
        BigInteger temp1 = uID.multiply(rID);
        //System.out.println("Counting b: y^h1");
        BigInteger temp2 = y.pow(h1);
        //System.out.println("Counting c: a*b");
        BigInteger temp3 = temp1.multiply(temp2);
        //System.out.println("Counting z1");
        BigInteger z1 = temp3.pow(my_tID+my_sID);

       //System.out.println("Counting z2");
        BigInteger z2 = uID.pow(my_tID);

        //System.out.println("Counting H2");
        BigInteger result = CClientConstraints.H2(z1.add(z2));
        //System.out.println("done");
        System.out.println("z1 to: " + z1);
        System.out.println("z2 to: " + z2);
        System.out.println("Klucz symetryczny to: " + result);
        //BigInteger result = new BigInteger("1234");
        return result;
    }
}
