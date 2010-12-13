/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Sebastian
 */
public class CServerConnectorThread extends Thread{

    private Socket socket = null;
    
    private InetAddress sourceAddress;
    
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

     public CServerConnectorThread(Socket socket) {
	super("CServerConnectionThread");
	this.socket = socket;
        this.sourceAddress = socket.getInetAddress();
    }

    public void run() {
        String inputLine, outputLine;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));

            outputLine = "hello, to ja serwer";
            System.out.println("wyslalem");
            System.out.println("->" + outputLine);
            printWriter.println(outputLine);

            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println("odczytalem");
                System.out.println("<-" + inputLine);

                System.out.println("wyslalem");
                System.out.println("->" + outputLine);
                printWriter.println(outputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    System.out.println("Killing Thread");
    }
}
