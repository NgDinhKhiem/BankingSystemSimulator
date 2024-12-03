package project.cs3360.socketserver.socket;

import project.cs3360.socketserver.handler.PacketHandler;
import project.cs3360.socketserver.handler.PacketListener;
import project.cs3360.socketserver.packet.Packet;
import project.cs3360.socketserver.utils.SortedList;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.util.*;

public class ServerHandler extends Thread {
    private final SocketClient client;
    private ObjectInputStream serverInput;
    private final Map<PacketListener, Set<Method>> registeredListener;
    private final Map<Method, String> registeredMethods;

    public ServerHandler(SocketClient client, ObjectInputStream serverInput, Map<PacketListener, Set<Method>> registeredListener, Map<Method, String> registeredMethods) {
        this.client = client;
        this.serverInput = serverInput;
        this.registeredListener = registeredListener;
        this.registeredMethods = registeredMethods;
    }
    @Override
    public void run() {
        try {
            Packet object = null;
            while (true) {
                try {
                    object = (Packet) serverInput.readObject();   
                }catch (EOFException e){
                    e.printStackTrace();
                }
                if(object==null)
                    continue;
                publishPacket(object);
            }
            //Process anything more if wanted
        }
        catch (SocketException e){
            System.out.println("SOCKET SERVER DISCONNECTED");
            this.client.reconnect();
            return;
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    private void publishPacket(Packet packet){
        Map<Method,PacketListener> revertedMap = new HashMap<>();
        SortedList<Method> filteredListeners = new SortedList<>(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                if(!o1.isAnnotationPresent(PacketHandler.class)||!o2.isAnnotationPresent(PacketHandler.class))
                    throw new RuntimeException("No PacketHandler Annotation found");
                return getPriority(o1)-getPriority(o2);
            }

            int getPriority(Method method){
                PacketHandler packetHandler = method.getAnnotation(PacketHandler.class);
                if(packetHandler==null)
                    return 0;
                return packetHandler.priority().getSlot();
            }
        });
        registeredListener.forEach((key,value)->{
            List<Method> listeners = new ArrayList<>(value);
            for(Method listener:listeners){
                String packetName = registeredMethods.get(listener);
                try {
                    if(!packet.getClass().getSimpleName().equals(packetName)&&!Class.forName(packetName).isAssignableFrom(packet.getClass()))
                        continue;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                revertedMap.put(listener,key);
                filteredListeners.add(listener);
            }
        });
        filteredListeners.forEach(listener-> {
            listener.setAccessible(true);
            try {
                listener.invoke(revertedMap.get(listener), packet);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            listener.setAccessible(false);
        });
    }
}
