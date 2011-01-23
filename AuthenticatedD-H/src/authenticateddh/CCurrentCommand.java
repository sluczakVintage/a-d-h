/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

/**
 *
 * @author Sebastian
 */
public class CCurrentCommand {

    //private String command = "none";

    private CCommandType command = CCommandType.CT_NONE;
    private static CCurrentCommand instance;

    //Singleton
    private CCurrentCommand()
    {
        System.out.println("CCurrentCommand");
    }

    public static synchronized CCurrentCommand getInstance() {
	if (instance == null) {
		instance = new CCurrentCommand();
	}
	return instance;
    }

    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }

    /**
     *
     * @param command
     * @return
     */
    public boolean equals(CCommandType command) {
        return this.command == command;
    }

    public synchronized  CCommandType getCurrentCommand() {
        return command;
    }

    public synchronized void setCurrentCommand(CCommandType command) {
        this.command = command;
    }

    public synchronized void clearCurrentCommand() {
        command = CCommandType.CT_NONE;
    }

}
