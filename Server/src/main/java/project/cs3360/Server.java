package project.cs3360;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server {
    private final int port;
    private final HttpServer server;
    private boolean isStarted = false;

    public Server(int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(port),0);
        this.registerContexts();
    }

    private void registerContexts(){

    }

    private void registerHandler(String route, HttpHandler handler){
        this.server.createContext(route, handler);
    }

    public void start(){
        if(isStarted)
            return;
        server.start();
        isStarted = true;
    }

    public static void main(String[] args){
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080),0);
            server.createContext("/hello", new HelloHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 8080");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static class HelloHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if(exchange.getRequestMethod().equals("GET")){
                String response = "Hello, World!";
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes(StandardCharsets.UTF_8));
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        }
    }
}
