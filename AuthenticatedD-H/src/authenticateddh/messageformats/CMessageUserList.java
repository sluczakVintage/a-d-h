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
public class CMessageUserList extends CMessage {


    private int ID;
    private String login;
    private String passwordHash;
    private CLoggedList loggedList;


    public void setUserList(CLoggedList cLoggedList) {
        this.loggedList = cLoggedList;
    }

    public CLoggedList getUserList() {
        return loggedList;
    }

    public CMessageUserList(CLoggedList loggedList) {
        this.loggedList = loggedList;
    }

    public CMessageUserList(int ID, String login) {
        this.login = login;
        this.ID = ID;
        this.loggedList = null;
    }

    public int getID() {
        return ID;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
