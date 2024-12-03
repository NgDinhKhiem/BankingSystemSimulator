package project.cs3360.socketserver.socket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SocketServer {
    private final ServerSocket server;
    private final Set<ObjectOutputStream> clientInstances = new HashSet<>();
    public SocketServer(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started");
        try {
            while (true) {
                Socket socket = server.accept();
                System.out.println("A Client accepted");
                new ClientHandler(socket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketServer(int port, List<String> approvedIPS) {
        final List<String> ips = new ArrayList<>(approvedIPS); //Clone the list - this should be immutable
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started");
        try {
            while (true) {
                Socket socket = server.accept();
                String clientIP = server.getInetAddress().getHostAddress();
                System.out.println("Connection from:"+ clientIP);
                if(!ips.contains(clientIP)){
                    System.out.println("Connection from:"+ clientIP +" is rejected!");
                    socket.close();
                    continue;
                }
                System.out.println("A Client accepted");
                new ClientHandler(socket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Set<ObjectOutputStream> getClientInstances() {
        return clientInstances;
    }
}
