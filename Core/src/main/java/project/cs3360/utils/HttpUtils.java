package project.cs3360.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public final class HttpUtils {
    public static void main(String[] args){
        CompletableFuture future = sendGETRequest("http://localhost:8080/hello").thenAccept((res)->{
            System.out.println(res);
        });

        // Optionally wait for the future to complete to ensure it prints before exiting (for demonstration only)
        future.join();
    }

    public static CompletableFuture<String> sendGETRequest(String uri) {
        return CompletableFuture.supplyAsync(() -> {
            StringBuilder result = new StringBuilder();
            HttpURLConnection connection = null;

            try {
                URL url = new URL(uri);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
//                if (responseCode == HttpURLConnection.HTTP_OK) { // success
//                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                        String line;
//                        while ((line = reader.readLine()) != null) {
//                            result.append(line);
//                        }
//                    }
//                } else {
//                    return "Request failed with response code: " + responseCode;
//                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                }
            } catch (IOException e) {
                return "Error: " + e.getMessage();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result.toString();
        });
    }

}
