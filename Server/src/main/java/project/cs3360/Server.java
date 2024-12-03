package project.cs3360;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import project.cs3360.handler.*;
import project.cs3360.manager.data.DataManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final HttpServer server;
    private boolean isStarted = false;
    private final DataManager dataManager = new DataManager();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public Server(int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port),0);
        this.server.setExecutor(executorService);
        this.registerContexts();
    }
    private void registerContexts(){
        registerHandler("/register", new AccountRegisterHandler(this));
        registerHandler("/balance", new AccountBalanceHandler(this));
        registerHandler("/info", new AccountInformationHandler(this));
        registerHandler("/verify", new AccountVerificationHandler(this));
        registerHandler("/send", new SendingMoneyHandler(this));
        registerHandler("/auth", new AuthenticationHandler(this));
    }
    private void registerHandler(String route, HttpHandler handler){
        this.server.createContext(route, handler);
    }
    public void start(){
        if(isStarted)
            return;
        server.start();
        System.out.println("Server started on port "+port);
        isStarted = true;
    }

    public void stop() {
        if (!isStarted) {
            return;
        }
        server.stop(0);
        executorService.shutdown(); // Shut down the executor
        System.out.println("Server stopped");
        isStarted = false;
    }
    public static void main(String[] args){
        try {
            final int PORT = 8080;
            Server server = new Server(PORT);
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
