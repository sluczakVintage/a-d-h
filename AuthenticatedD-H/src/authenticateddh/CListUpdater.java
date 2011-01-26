/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sebastian
 */
public class CListUpdater extends Thread{

        private static CListUpdater instance;
        private boolean enabled;

    private CListUpdater()
    {
        System.out.println("CListUpdater");
        enabled = false;
    }

    public static synchronized CListUpdater getInstance() {
	if (instance == null) {
		instance = new CListUpdater();
	}
	return instance;
    }

    /// @Override clone nadpisanie
    @Override
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    @Override
    public void run() {


        while(true) {
            while(enabled) {
                try {
                    CCurrentCommand.getInstance().setCurrentCommand(CCommandType.CT_LIST);
                    while (!enabled) {
                        sleep(10000);
                    }
                    sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CListUpdater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void setDisabled() {
            enabled = false;
    }

    public void setEnabled() {
        enabled = true;
    }

}
