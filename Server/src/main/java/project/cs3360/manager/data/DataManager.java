package project.cs3360.manager.data;

import project.cs3360.socketserver.socket.SocketClient;

public class DataManager {
    private static final String address = "example.com";
    private static final int PORT = 80;
    private SocketClient socketClient;
    public DataManager(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //socketClient = new SocketClient(address,PORT);
            }
        }).start();
    }
}
