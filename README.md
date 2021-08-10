# Shared White Board

This repository provides implementation of shared whiteboards that allow multiple user to draw simultaneously on a canvas.  In this whiteboard system, user can able to draw basic shapes like line, circle and rectangle in shared board and those changes are reflected over all the users who have joined the board. The application mainly utilizes centralized server architecture with the RMI framework and multithreaded client.

RMI is a lightweight that makes it efficient for such systems and JVM comprises this framework so no need to worry about the installation of a server that makes this system more portable. The multi-threaded client uses two threads one for GUI and another for communication and that makes the system much more flexible and interactive as it discards the possibility of UI locking for server communication.


## Functionalities

* Multiple users can draw on a shared interactive canvas.
* A single whiteboard that is shared between all of the clients.
* The first user who creates the whiteboard over a server will become a host for that board.
* Allow host to allow and kick out a certain users.
* Whiteboard supports line, circle, rectangle and text. 

## Setup of Shared White Board

### 1st step: clone the project

```
git clone https://github.com/avpatel26/SharedWhiteBoard.git
cd SharedWhiteBoard/
```

### 2nd step: Start a Server

This class implements the ServerInterface and works as a central server.This jar file creates server on port 5000. That is hard coded and can be updated in server.java file.

```
java –jar Server.jar
```

### 3rd step: Create a whiteboard (Host)

This performs RMI lookup operation and connects to the server. It also creates a GUI for application. This class starts a thread for checking the server for events and displays GUI accordingly.

```
java -jar CreateWhiteBoard.jar <server-address> <port> <username>
```

server-address : adreess of server (e.g. localhost) \
port : port number on that server is running 
username: Name of user to display on a whiteboard

### 4th step: Join a whiteboard

This sends the notification to host user for allowing new user to join the whiteboard.

```
java -jar JoinWhiteBoard.jar <server-address> <port> <username>
```

server-address : adreess of server (e.g. localhost) \
port : port number on that server is running 
username: Name of user to display on a whiteboard
