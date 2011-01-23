/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CUser;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author Sebastian
 */
public class CUserManager {
    //CUserMap
    
    TreeMap<Integer, User> cUserMap;
    private static int ID;
    //SL: Singleton
    private static CUserManager instance;

    public static synchronized CUserManager getInstance() {
	if (instance == null) {
		instance = new CUserManager();
	}
	return instance;
    }

    private CUserManager()
    {
        cUserMap = new TreeMap();
        System.out.println("CUserManager");
        ID = 0;
    }

    synchronized public int addUser(String nickname, String passwordHash, InetAddress address) {
        ID++;
        KeyPair userKeyPairs = KeyGenerationCenter.getInstance().generateKeys(ID);
        cUserMap.put(ID, new User(ID, nickname, passwordHash, KeyGenerationCenter.getInstance().getGenerator_(), KeyGenerationCenter.getInstance().getY_(),userKeyPairs.getsID_(), userKeyPairs.getrID_(),  address, true));
        
        return ID;
    }

    synchronized public int setUserAvailable(int ID, String nickname, String passwordHash, InetAddress address, boolean available) {
        
        User user = cUserMap.get(ID);
        user.setAvailability(available);
        user.setNickname(nickname);
        user.setInetAddress(address);
        cUserMap.put(ID, user);
        
        return ID;
    }

    synchronized public int setUserAvailable(int ID, boolean available) {

        User user = cUserMap.get(ID);
        user.setAvailability(available);
        cUserMap.put(ID, user);

        return ID;
    }

    synchronized public User getUser(int ID) {
        return cUserMap.get(ID);
    }

    synchronized public CLoggedList getLoggedList(int ID) {
        CLoggedList list = new CLoggedList();
        User tempUser;
        
        Collection c = cUserMap.values();
        Iterator itr = c.iterator();
        while(itr.hasNext()) {
            
                tempUser = ((User)(itr.next()));
                //przesyłaj tylko dostępnych
                if((tempUser.getID() != ID) && tempUser.getAvailability()) {
                CUser cUser = new CUser(tempUser.getID(), tempUser.getNickname(), tempUser.getInetAddress(), tempUser.getAvailability());
                list.add(cUser);
            }
        }

        return list;
    }

}
