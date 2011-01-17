/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.net.InetAddress;
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

    public int addUser(String nickname, String passwordHash, InetAddress address) {
        ID++;
        cUserMap.put(ID, new User(ID, nickname, passwordHash, KeyGenerationCenter.getInstance().getGenerator_(), KeyGenerationCenter.getInstance().getY_(), address, true));
        
        return ID;
    }

    public int setUserAvailable(int ID, String nickname, String passwordHash, InetAddress address, boolean available) {
        
        User user = cUserMap.get(ID);
        user.setAvailability(available);
        user.setNickname(nickname);
        user.setInetAddress(address);
        cUserMap.put(ID, user);
        
        return ID;
    }

    public User getUser(int ID) {
        return cUserMap.get(ID);
    }


}
