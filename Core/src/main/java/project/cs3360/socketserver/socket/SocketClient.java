package project.cs3360.socketserver.socket;

import project.cs3360.socketserver.handler.PacketHandler;
import project.cs3360.socketserver.handler.PacketListener;
import project.cs3360.socketserver.packet.Packet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SocketClient {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream serverInput;
    private final Map<PacketListener, Set<Method>> registeredListener = new HashMap<>();
    private final Map<Method, String> registeredMethods = new HashMap<>();
    private final String address;
    private boolean runningState = false;
    private final int port;
    public SocketClient(String address, int port){
        this.address = address;
        this.port = port;
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            runningState = true;
            output = new ObjectOutputStream(socket.getOutputStream());
            serverInput = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            new Thread(new ServerHandler(this, serverInput, registeredListener, registeredMethods)).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reconnect(){
        close();
        System.out.println("ATTEMPTS TO RECONNECT");
        try {
            socket = new Socket(address, port);
            output = new ObjectOutputStream(socket.getOutputStream());
            serverInput = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            new Thread(new ServerHandler(this, serverInput, registeredListener, registeredMethods)).start();
        } catch (IOException e) {
            try {
                System.out.println("RECONNECT FAIL ATTEMPT TO RECONNECT IN 5s");
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            reconnect();
            return;
        }
        System.out.println("ReConnected");
        runningState = true;
    }
    public void close(){
        try {
            socket.close();
            output.close();
            socket.close();
            serverInput.close();
            runningState = false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Packet packet){
        if(!runningState)
            return;
        try {
            output.writeObject(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerListener(PacketListener packetListener){
        for(Method method:packetListener.getClass().getDeclaredMethods()){
            if(!method.isAnnotationPresent(PacketHandler.class))
                continue;
            if(method.getParameters().length!=1)
                continue;
            Parameter parameter = method.getParameters()[0];
            if(!Packet.class.isAssignableFrom(parameter.getType())){
                continue;
            }
            addNewListener(packetListener,method, parameter.getType().getName());
        }
    }

    private void addNewListener(PacketListener packetListener, Method method, String packetName){
        Set<Method> listMethod = this.registeredListener.computeIfAbsent(packetListener, k -> new HashSet<>());
        listMethod.add(method);
        registeredMethods.put(method, packetName);
    }

    public void unRegisterAll(PacketListener packetListener){
        Set<Method> listMethod = this.registeredListener.get(packetListener);
        if(listMethod!=null){
            listMethod.forEach(registeredMethods::remove);
            listMethod.clear();
        }
        this.registeredListener.remove(packetListener);
    }
}
