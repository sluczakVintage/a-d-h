/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import authenticateddh.messageformats.CUser;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
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
         KeyPair userKeyPairs;
        //przejrzyj mape uzytkownikow w poszukiwaniu tego konkretnego
        for (Iterator it = cUserMap.entrySet().iterator(); it.hasNext();) {
           Map.Entry entry = (Map.Entry) it.next();
           int key = (Integer)(entry.getKey());
           User value = (User)(entry.getValue());

           User tempUser = value;
            
            if(  (tempUser.getNickname()).equals(nickname)  ) {
                //jesli ustnieje taki nickname i zgadza sie haslo i user nie jest teraz dostepny
                if( (tempUser.getPasswordHash()).equals(passwordHash) && !tempUser.getAvailability() ) {
                    userKeyPairs = KeyGenerationCenter.getInstance().generateKeys(key);
                    cUserMap.put( key, new User(ID, nickname, passwordHash, KeyGenerationCenter.getInstance().getGenerator_(), KeyGenerationCenter.getInstance().getY_(),userKeyPairs.getsID_(), userKeyPairs.getrID_(),  address, true));
                    return key;
                }
                //jesli istnieje nickname, ale haslo sie nie zgadza
                else  {
                    return 0;
                }
            }
        }
           //jesli nie istnieje, stworz nowego
        
        ID++;
        userKeyPairs = KeyGenerationCenter.getInstance().generateKeys(ID);
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
                if( (tempUser.getID() != ID) ) {
                    CUser cUser = new CUser(tempUser.getID(), tempUser.getNickname(), tempUser.getInetAddress(), tempUser.getAvailability());
                    list.add(cUser);
            }
        }

        return list;
    }

}
