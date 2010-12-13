/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

/**
 *
 * @author Sebastian
 */
public class CClientConstraints {

    public static final int TCP_SSL_PORT = 25801;
    public static final int TCP_PORT = 25802;
    public static final String KEY_PATH_CLIENT = "./keys/clientstore";
    public static final String KEY_PATH_TRUSTED = "./keys/cacerts";

    public static final String APP_PATH = "./config/AppLists/";
    public static final String SERVER_LIST_PATH = "./config/IpList.dat";

    public static final String SERVER_IP = "192.168.1.101";

}

