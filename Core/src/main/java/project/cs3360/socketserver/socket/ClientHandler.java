package project.cs3360.socketserver.socket;

import project.cs3360.socketserver.handler.IgnoreSelf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Set<ObjectOutputStream> clientInstances;

    public ClientHandler(Socket socket, SocketServer server) {
        this.socket = socket;
        this.clientInstances = server.getClientInstances();
        try {
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            synchronized (clientInstances) {
                clientInstances.add(out);
            }
            while (true) {
                publish(in.readObject());
            }
        }
        catch (SocketException e){
            clientInstances.remove(out);
            System.out.println("A Client disconnected");
        }
        catch (ClassNotFoundException|IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                synchronized (clientInstances) {
                    clientInstances.remove(out);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void publish(Object object) {
        synchronized (clientInstances) {
            for (ObjectOutputStream clientOut : clientInstances) {
                if(clientOut.equals(out)){
                    if(object.getClass().isAnnotationPresent(IgnoreSelf.class))
                        continue;
                }
                try {
                    clientOut.writeObject(object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
