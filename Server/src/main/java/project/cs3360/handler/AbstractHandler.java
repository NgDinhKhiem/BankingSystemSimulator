package project.cs3360.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import project.cs3360.Server;
import project.cs3360.object.Param;
import project.cs3360.object.Response;
import project.cs3360.object.ResponseCode;
import project.cs3360.object.TypeHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public abstract class AbstractHandler implements HttpHandler {
    protected final Server server;
    private final String method;

    public AbstractHandler(Server server, String method) {
        this.server = server;
        this.method = method;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(this.method)){
            Map<String, String> paras = this.parseQueryParams(exchange.getRequestURI().getQuery());
            Class<? extends AbstractHandler> child = this.getClass();
            List<Field> fields = new java.util.ArrayList<>((List.of(child.getDeclaredFields())));
            fields.removeIf(field -> !field.isAnnotationPresent(Param.class));
            System.out.println(exchange.getRequestURI()+ " "+this.getClass().getSimpleName());
            boolean isRaw = false;
            if(paras.containsKey("raw")) {
                isRaw = true;
                paras.remove("raw");
            }
            if(paras.size()!=fields.size()) {
                System.out.println("SIZE DIFFERENT");
                exchange.sendResponseHeaders(ResponseCode.BAD_REQUEST.getCode(), -1);
                return;
            }
            for(Field field:fields){
                if(!paras.containsKey(field.getName())){
                    for(String key: paras.keySet()){
                        System.out.print(key+ " ");
                    }
                    System.out.println("INVALID FIELDS "+field.getName());
                    exchange.sendResponseHeaders(ResponseCode.BAD_REQUEST.getCode(), -1);
                    return;
                }
                field.setAccessible(true);
                try {
                    field.set(this,
                            TypeHandler.getAdapter(field.getType()).convert(paras.get(field.getName())));
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    exchange.sendResponseHeaders(ResponseCode.BAD_REQUEST.getCode(), -1);
                    return;
                }
            }
            Response response = this.resolve();
            String jsonBody = isRaw?response.getResponse().toJSON():response.getResponse().serialize();
            exchange.sendResponseHeaders(response.getCode().getCode(), jsonBody.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
            }
        }else {
            exchange.sendResponseHeaders(ResponseCode.BAD_REQUEST.getCode(),-1);
        }
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> queryParams = new java.util.HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return queryParams;
    }

    protected abstract Response resolve();
}
