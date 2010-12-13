/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package authenticateddh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Sebastian
 */
public class CClientConnector {
    private static CClientConnector instance;

    // Gniazdko klienta
    private Socket clientSocket;
    
    // Czytanie i pisanie, potrzebne do operacji na gniezdzie
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;


    //Singleton
    private CClientConnector()
    {
        System.out.println("CConnector");
    }

    public static synchronized CClientConnector getInstance() {
	if (instance == null) {
		instance = new CClientConnector();
	}
	return instance;
    }

    /// @Override clone nadpisanie
    public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
    }
    //polaczenie
    synchronized public boolean connect(String ip)
    {
        try {
            System.out.println("Connection started");
            standardConnect(ip, CClientConstraints.TCP_PORT);
            standardCommunication();
            disconnect();
        } catch (UnknownHostException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            Logger.getLogger(CClientConnector.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    //podlaczenie
    synchronized private void standardConnect(String hostname, int port) throws UnknownHostException, IOException
    {
        clientSocket = new Socket( hostname, port );
        System.out.println("Standard connection completed");
    }

    synchronized private void disconnect() throws UnknownHostException, IOException
    {
        clientSocket.close();
        System.out.println("Disconnected");
    }

    //komunikacja
    synchronized private boolean standardCommunication() throws IOException
    {
        String inputLine, outputLine;
        try {
                InputStream is = clientSocket.getInputStream();
                OutputStream os = clientSocket.getOutputStream();

                printWriter = new PrintWriter(os, true);
                bufferedReader = new BufferedReader(
				    new InputStreamReader(
				    is));

                
                outputLine = "hello, to ja klient";
                System.out.println("wyslalem");
                System.out.println("->" + outputLine);
                printWriter.println(outputLine);

                inputLine = bufferedReader.readLine();
                System.out.println("odczytalem");
                System.out.println("<-" + inputLine);
                
                printWriter.close();
                bufferedReader.close();

        } catch (SocketException ex) {
            System.err.println(ex.getMessage());
        } catch (NullPointerException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
