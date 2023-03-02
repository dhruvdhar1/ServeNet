package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import static client.ClientApp.preloadComplete;
import static client.ClientApp.preloadDataInd;
import static client.ClientApp.resetReconnectAttemptInd;


/**
 * This class attempts to make a connection to the TCP socket open on the remote server, and
 * facilitates TCP socket communication between the client and server.
 */
public class TCPClient extends AbstractClient {

  @Override
  public void handleConnection(String host, int port) throws IOException {
//    int preloadDataInd = 0;
    Socket clientSock = new Socket(host, port); //start a socket connection on `host:port`
    clientSock.setSoTimeout(TIMEOUT); //request will timeout after 2 seconds
    ClientLogger.info(String.format("Socket connection started on %s:%s", host, port));
    resetReconnectAttemptInd();
    //create input and output streams
    DataInputStream inStream = new DataInputStream(clientSock.getInputStream());
    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
    PrintWriter writer = new PrintWriter(clientSock.getOutputStream(), true);

    Scanner sc = new Scanner(System.in);

    while (true) {
      if (preloadComplete) {
        System.out.print("\nEnter request: ");
        String input = sc.nextLine();
        writer.println(input);
      } else {
        //pre-populate data
        String command = super.getDataToPrepopulate().get(preloadDataInd++);
        System.out.println("\nExecuting Request: " + command);
        writer.println(command);
        if (preloadDataInd == getDataToPrepopulate().size()) {
          preloadComplete = true;
        }
      }
      try {
        String response = reader.readLine();
        ClientLogger.info("Response: " + response);
      } catch (SocketTimeoutException ex) {
        clientSock.close();
        throw new IOException("Terminating Socket connection");
      }
    }
  }
}
