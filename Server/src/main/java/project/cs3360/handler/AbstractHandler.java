package project.cs3360.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public abstract class AbstractHandler implements HttpHandler {
    private final String method;

    public AbstractHandler(String method) {
        this.method = method;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(this.method)){
            Response response = this.resolve(exchange);
            exchange.sendResponseHeaders(response.getCode().getCode(), response.getResponse().length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
            }
        }else {
            exchange.sendResponseHeaders(ResponseCode.BAD_REQUEST.getCode(),-1);
        }
    }

    protected abstract Response resolve(final HttpExchange exchange);
}
