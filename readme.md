## Project1: Singlethreaded Key-Value store
This client-server application is used to store key-value pairs on the server. The client makes a 
request to the server with request type and request data and the server executes the request.
The server supports 3 types of operation:\

PUT: This is used to store key-value pairs on the server.\
GET: This is used to fetch values pertaining to a particular key from the server.\
DELETE: This is used to delete an entry pertaining to a key from the server.\

This client-server application uses a custom protocol to execute requests. This protocol is 
textual and has the following format:
```java
<method>/<data>
```

For PUT operation, pass the request in the form: ```PUT/key:value``` \
example: ```PUT/hello:world```\
This will store the entry ```<hello: world>``` on the server.

For GET, pass the request as ```GET/key``` \
example: ```GET/hello```\
This will return the value `world`.

Similarly, for DELETE, pass the request as ```DELETE/key```\
example: ```DELETE/hello```\
This will delete `<hello, world>` entry from the key-value store.

Both client and server application supports TCP and UDP. Usage is described below:

##Running Server on TCP:
to run the server on TCP, follow these steps
```java
- compile all the server classes.
- Run ServerApp.java and pass port and protocol(tcp) as argument.
```

example:
```java
javac server/*.java
java server/ServerApp 4000 tcp
```

Alternatively, jars are also available if you directly want to run the application. Steps for running the jar:
```java
java -jar server.jar 4000 tcp
```

This will run the server in TCP mode on port 4000.

####Important Note: Please use Ctrl-c ONLY to stop the server. Ctrl-z does not kill the process entirely.

##Running Client that connects to TCP Server:
to run the client for TCP Server, follow these steps
```
- compile all the client classes.
- Make sure that server is running on TCP.
- Run ClientApp.java and pass host, port and protocol(tcp) as argument.
```

example:
```java
javac client/*.java
java server/ServerApp 4000 tcp

In another terminal,
java client/ClientApp 127.0.0.1 4000 tcp
```

Alternatively, jar for client is also available if you directly want to run the client. Steps for running the jar:
```java
java -jar client.jar 127.0.0.1 4000 tcp
```

This will connect the client to the TCP server. The user can now send requests.
####Important Note: Please use Ctrl-c ONLY to stop the client. Ctrl-z does not kill the process entirely.

##Running Server on UDP:
Steps for running the server on UDP will be similar to that of TCP. We just need to 
pass udp in argument for the protocol.

example:
```java
javac server/*.java
java server/ServerApp 4000 udp
```

This will run the server in UDP mode on port 4000.
####Important Note: Please use Ctrl-c ONLY to stop the server. Ctrl-z does not kill the process entirely.

##Running Client that connects to UDP Server:
Steps for running the client for UDP Server will be similar to that of TCP. We just need to
pass udp in argument for the protocol.

example:
```java
javac client/*.java
java server/ServerApp 4000 udp

In another terminal,
java client/ClientApp 127.0.0.1 4000 udp
```

This will connect the client to the TCP server. The user can now send requests.
####Important Note: Please use Ctrl-c ONLY to stop the client. Ctrl-z does not kill the process entirely.

On running the client, the client first executes a set of hardcoded PUT requests to pre-populate the server 
and then executes 5 GET and 5 DELETE requests as required (5 PUT already executed before).
After that user will get a prompt to Enter request.

####NOTE 1: A socket timeout of 2000 ms is set on the client side. Any request that takes more than 2000ms will be discarded.

####NOTE 2: Any malformed request will be ignored by the server and the server will send an error message to the client.

####NOTE 3: Client logs are printed on stdout itself along with other prompts.


The response may contain status codes which are described below:

##Response status codes
200: OK\
404: Not found

