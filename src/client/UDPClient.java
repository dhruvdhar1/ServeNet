package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import server.ServerLogger;

import static client.ClientApp.preloadComplete;
import static client.ClientApp.preloadDataInd;


/**
 * This class attempts to make a connection to the UDP socket open on the remote server, and
 * facilitates UDP socket communication between the client and server.
 */
public class UDPClient extends AbstractClient {

  private static final int MAX_DATA_LENGTH = 65535;

  @Override
  public void handleConnection(String host, int port) throws IOException {
    Scanner sc = new Scanner(System.in);
    DatagramSocket socket = new DatagramSocket();
    socket.setSoTimeout(TIMEOUT);

    byte[] data = null;
    while (true) {
      if (preloadComplete) {
        System.out.print("\nEnter request: ");
        String str = sc.nextLine();
        data = str.getBytes();
      } else {
        String command = super.getDataToPrepopulate().get(preloadDataInd++);
        System.out.println("\nExecuting Request: " + command);
        data = command.getBytes();
        if (preloadDataInd == getDataToPrepopulate().size()) {
          preloadComplete = true;
        }
      }

      InetAddress serverIp = InetAddress.getByName(host);
      DatagramPacket packet = new DatagramPacket(data, data.length, serverIp, port);
      ClientLogger.info(String.format("Sending datagram packet to %s:%s", host, port));
      socket.send(packet);

      //fetching response from server.
      byte[] responseData = new byte[MAX_DATA_LENGTH];
      DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
      socket.receive(responsePacket);
      String responseStr = new String(responseData, 0, responsePacket.getLength());
      ClientLogger.info("Response: " + responseStr);
    }
  }
}
