package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that exposes some common functionality between TCPClient and UDPClient.
 */
public abstract class AbstractClient {

  static final int TIMEOUT = 2000;

  /**
   * Attempts to connect to TCP/UDP socket on the remote server.
   * @param host remote server hostname.
   * @param port remote server port.
   * @throws IOException if socket connection is unsuccessful.
   */
  public abstract void handleConnection(String host, int port) throws IOException;

  /**
   * This method returns a list of hardcoded client requests to be executed on the server.
   * @return list of requests
   */
  public List<String> getDataToPrepopulate() {
    List<String> requests = new ArrayList<>();
    requests.add("PUT/foo:bar");
    requests.add("PUT/distributed:systems");
    requests.add("PUT/hello:world");
    requests.add("PUT/dhruv:dhar");
    requests.add("PUT/summer:high");

    requests.add("GET/dhruv");
    requests.add("GET/summer");
    requests.add("GET/foo");
    requests.add("GET/distributed");
    requests.add("GET/hello");

    requests.add("DELETE/hello");
    requests.add("DELETE/foo");
    requests.add("DELETE/distributed");
    requests.add("DELETE/dhruv");
    requests.add("DELETE/summer");

    requests.add("PUT/foo:bar");
    requests.add("PUT/distributed:systems");
    requests.add("PUT/hello:world");
    requests.add("PUT/dhruv:dhar");
    requests.add("PUT/summer:high");

    return requests;
  }
}
