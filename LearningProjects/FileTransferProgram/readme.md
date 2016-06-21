This project consists of a file transfer server and a file transfer client.

The file transfer server is given a working directory then opens a socket and awaits connection from the client. When the client connects the server runs the connection in its own thread allowing multiple connections to be run on the server at the same time. The client can then send commands to view files in the working directory, to delete files from the working directory, to upload files to the working directory, and to download files from the working directory.
