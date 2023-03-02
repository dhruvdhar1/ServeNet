package client;

import java.io.IOException;

/**
 * This class exposes the main method of the Client for the Key value store application
 */
public class ClientApp {

  public static boolean preloadComplete = false;
  public static int preloadDataInd = 0;
  private static int maxReconnectAttempt = 0;

  public static void resetReconnectAttemptInd() {
    maxReconnectAttempt = 0;
  }
  /**
   * Starting point for execution of the Client program. The main method expects 3 arguments which
   * are the hostname, port and the communication protocol.
   * @param args program arguments
   */
  public static void main(String[] args) {
    AbstractClient client;
    try {
      if(args.length < 3) {
        System.out.println("invalid arguments, usage: java ClientApp <host> <port> <protocol>");
        return;
      }
      String host = args[0];
      String portStr = args[1];
      String protocol = args[2];
      int portNum = Integer.parseInt(portStr);
      if(protocol.equalsIgnoreCase("tcp")) {
        client = new TCPClient();
      } else if(protocol.equalsIgnoreCase("udp")) {
        client = new UDPClient();
      } else {
        throw new IllegalStateException("Invalid protocol provided..try again");
      }
      client.handleConnection(host, portNum);
    } catch (IllegalStateException ex) {
      ClientLogger.error(ex.getMessage());
    } catch (IOException ex) {
      ClientLogger.error("Connection timeout");
      if(maxReconnectAttempt >= 5) {
        ClientLogger.error("Max reconnection attempts reached..check server connection");
        return;
      } else {
        maxReconnectAttempt++;
      }
      ClientLogger.error("Attempting to reconnect...");
      main(args);
    }
  }
}
