package server;

import java.io.IOException;

/**
 * This class exposes the main method of the Server for the Key value store application
 */
public class ServerApp {

  static AbstractHandler handler;

  /**
   * Starting point for execution of the Server program. The main method expects 2 arguments which
   * are the port on which, the server needs to run and the communication protocol.
   * @param args program arguments
   */
  public static void main(String[] args) {
    try {
      if(args.length < 1) {
        System.out.println("invalid arguments, usage: java ServerApp <port> <protocol>");
        return;
      }
      String portStr = args[0];
      String protocol = args[1];
      int portNum = Integer.parseInt(portStr);

      if(protocol.equalsIgnoreCase("tcp")) {
        handler = new TCPHandler();
      } else if (protocol.equalsIgnoreCase("udp")) {
        handler = new UDPHandler();
      } else {
        System.out.println("Invalid protocol provided. Please run the program with correct values.");
        return;
      }
      handler.handleConnection(portNum);
    } catch (IOException e) {
      ServerLogger.error("Error occurred while trying to start server");
      e.printStackTrace();
    }
  }
}

/**
todo:
 add timout logic
 client logs in file
*/
