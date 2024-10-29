package project.cs3360.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public final class HttpUtils {
    public static CompletableFuture<String> sendGetRequest(String uri){
        return CompletableFuture.supplyAsync(()->{
            URL url = null;
            try {
                url = new URL(uri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

            } catch (IOException e) {
                return "Null";
            }
            return new CompletableFuture<>();
        });
    }
}
