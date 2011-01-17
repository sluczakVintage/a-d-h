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

    private String command = "none";

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

    public synchronized  String getCurrentCommand() {
        return command;
    }

    public synchronized void setCurrentCommand(String command) {
        this.command = command;
    }

    public synchronized void clearCurrentCommand() {
        command = "none";
    }

}
