package server;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Abstract class that exposes some common functionality between TCPHandler and UDPHandler.
 */
public abstract class AbstractHandler {

  private final KeyValue keyValueStore = KeyValue.getInstance();

  /**
   * Opens a TCP/UDP socket connection on the user provided port.
   * @param port port number to start the socket on.
   * @throws IOException if something goes during socket communication
   */
  public abstract void handleConnection(int port) throws IOException;

  /**
   * Decodes incoming data packet. Every packet should have the following structure:
   * method/key, for example: GET/foo OR
   * method/key:value, for example: POST/foo:bar
   * @param payload packet string
   * @throws IllegalArgumentException if payload is corrupted/ malformed.
   */
  String decodePacket(String payload, InetAddress host, int port) throws IllegalArgumentException {
    //split method and data
    String[] packetTokens = payload.split("/");
    if(packetTokens.length != 2) {
      throw new IllegalArgumentException(String.format("Malformed/Corrupted request received " +
              "from %s:%s", host.getHostAddress(), port));
    }
    String response = null;
    String method = packetTokens[0];
    switch(method) {
      case "GET":
      case "get":
        String result = keyValueStore.get(packetTokens[1]);
        if(result != null) {
          response = "status: 200, data: value=" + result;
        } else {
          response = "status: 404, data: key not found";
        }
        break;

      case "PUT":
      case "put":
        String[] keyVal = packetTokens[1].split(":");
        if(keyVal.length != 2) {
          throw new IllegalArgumentException("Invalid key/ value pair");
        }
        keyValueStore.put(keyVal[0], keyVal[1]);
        response = "status: 200, data: operation completed successfully";
        break;

      case "DELETE":
      case "delete":
        boolean deleteRes = keyValueStore.delete(packetTokens[1]);
        if(deleteRes) {
          response = "status: 200, data: operation completed successfully";
        } else {
          response = "status: 404, data: key not found";
        }
        break;

      default:
        throw new IllegalArgumentException("Invalid method provided");
    }
    return response;
  }
}
