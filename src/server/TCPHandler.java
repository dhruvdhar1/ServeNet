package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * This class handles TCP socket connection between the server and the client.
 */
public class TCPHandler extends AbstractHandler {

  private ServerSocket sock;
  private DataInputStream inStream;
  private BufferedReader reader;
  private PrintWriter writer;
  private Socket clientSock;

  @Override
  public void handleConnection(int port) throws IOException {
    sock = new ServerSocket(port);
    ServerLogger.info(String.format("TCP Server started on port %d", port));

    while (true) {
      //accept client connects to socket
      clientSock = sock.accept();
      inStream = new DataInputStream(clientSock.getInputStream());
      reader = new BufferedReader(new InputStreamReader(inStream));
      writer = new PrintWriter(clientSock.getOutputStream(), true);
      String message;

      try {
        while (!clientSock.isClosed() && (message = reader.readLine()) != null) {
          try {
            InetAddress clientIp = clientSock.getInetAddress();
            int clientPort = clientSock.getPort();
            ServerLogger.info(String.format("Request received: %s, from Client: %s:%s",
                    message, clientIp.getHostAddress(), clientPort));
            String result = super.decodePacket(message, clientIp, clientPort);
            ServerLogger.info(String.format("Sending response: %s, IP=%s:%s", result,
                    clientIp.getHostAddress(), clientPort));
            writer.println(result);
          } catch (IllegalArgumentException ex) {
            ServerLogger.error(ex.getMessage());
            writer.println("Malformed/ Corrupted request received..Please try again");
          }
        }
      } catch (SocketException soex) {
        ServerLogger.info("Socket exception occurred. " +
                "Reopening socket. Details: " + soex.getMessage());
      }

    }
  }
}

