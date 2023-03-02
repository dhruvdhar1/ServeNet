package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class handles TCP socket connection between the server and the client.
 */
public class UDPHandler extends AbstractHandler {

  private static final int MAX_DATA_LENGTH = 65535;

  @Override
  public void handleConnection(int port) throws IOException {
    DatagramSocket socket = new DatagramSocket(port);
    ServerLogger.info("UDP Server started on port " + port);
    byte[] data = new byte[MAX_DATA_LENGTH];
    DatagramPacket requestPacket = null;
    while (true) {
      requestPacket = new DatagramPacket(data, data.length);
      socket.receive(requestPacket);
      String message = new String(data, 0, requestPacket.getLength());
      ServerLogger.info("Request received: " + message);

      try {
        //Decoding packet
        InetAddress clientIp = requestPacket.getAddress();
        int clientPort = requestPacket.getPort();
        String result = super.decodePacket(message, clientIp, clientPort);
        ServerLogger.info(String.format("Sending response: %s, Client=%s:%s", result,
                clientIp.getHostAddress(), clientPort));
        sendResponse(socket, requestPacket, result);
      } catch (IllegalArgumentException ex) {
        ServerLogger.error(ex.getMessage());
        String errorMsg = "Malformed/ Corrupted request received..Please try again";
        sendResponse(socket, requestPacket, errorMsg);
      }
      //clear input buffer
      data = new byte[MAX_DATA_LENGTH];
    }
  }

  /**
   * This method abstracts the process of sending response back to the client.
   *
   * @param socket   reference to the socket
   * @param request  reference to the request packet.
   * @param response response to send to the client.
   * @throws IOException if response cannot be sent.
   */
  private void sendResponse(DatagramSocket socket, DatagramPacket request, String response)
          throws IOException {
    byte[] responseByte = response.getBytes();
    InetAddress clientAddress = request.getAddress();
    int clientPort = request.getPort();
    DatagramPacket responsePacket = new DatagramPacket(responseByte, responseByte.length,
            clientAddress, clientPort);
    socket.send(responsePacket);
  }
}
