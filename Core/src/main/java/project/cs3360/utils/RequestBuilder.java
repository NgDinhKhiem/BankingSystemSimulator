package project.cs3360.utils;

import project.cs3360.object.RequestParameter;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RequestBuilder {
    private final String uri;
    private List<RequestParameter> parameters = new ArrayList<>();

    public RequestBuilder(String uri) {
        this.uri = uri;
    }

    public RequestBuilder(String uri, RequestParameter... parameters) {
        this.uri = uri;
        Collections.addAll(this.parameters, parameters);
    }

    public void add(RequestParameter para){
        this.parameters.add(para);
    }

    public static void main(String[] args) {
        //https://example.com/
        RequestBuilder rb = new RequestBuilder("http://localhost:8080/hello", new RequestParameter("id", "1234312"), new RequestParameter("token", "9474895"), new RequestParameter("khiem", "7483974"));
        System.out.println(rb.getRequest());
        long start = System.currentTimeMillis();

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            System.out.println("Starting request: " + i);
            CompletableFuture<Void> future = CompletableFuture.runAsync(new myThread(i));
            futures.add(future);

        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        System.out.println("All requests completed");
        long totalTime = System.currentTimeMillis() - start;
        System.out.println("System Running: " + totalTime);


    }
    public String getRequest(){
        int count = 0;
        StringBuilder uriStringBuilder = new StringBuilder(uri);
        uriStringBuilder.append("?");
        for(RequestParameter para: parameters) {
            count += 1;
            uriStringBuilder.append(para.toURI());
            if (count < parameters.size()){
                uriStringBuilder.append("&");
            }

        }

        return uriStringBuilder.toString();
    }
}
