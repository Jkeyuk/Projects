This project consists of two applications.

  The first application is the server. When the server starts up it finds an unused port and opens it up for connections. 
When it recieves a connection, it creates a chat connection object and runs it in its own thread for each connection. The chat connection
object then listens over the socket for incoming messages, and then brodcasts those messages to each person connected to the server.

  The second application is the client. The client is used to connect to the chat server after asking the user for the ip and port of the
Server to connect to. Once the client connects to the server they can then start chatting by inputing messages into the terminal window. All of the chatting is done through the terminal window.
