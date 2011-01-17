/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh.messageformats;

import authenticateddh.CLoggedList;

/**
 *
 * @author Sebastian
 */
public class CMessageLogin extends CMessage {

    private int ID;
    private String login;
    private String passwordHash;
    private CLoggedList loggedList;

    public CMessageLogin(CLoggedList loggedList) {
        this.ID = 0;
        this.loggedList = loggedList;
    }

    public CMessageLogin(int ID, String login, String passwordHash) {
        this.ID = ID;
        this.login = login;
        this.loggedList = null;
    }

    public int getID() {
        return ID;
    }

    public String getLogin() {
        return login;
    }

    public CLoggedList getLoggedList() {
        return loggedList;
    }


    public String getPasswordHash() {
        return passwordHash;
    }


}
