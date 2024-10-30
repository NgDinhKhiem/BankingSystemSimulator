package project.cs3360.utils;

import project.cs3360.object.RequestParameter;

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

    public static void main(String[] args){
        //https://example.com/
        RequestBuilder rb = new RequestBuilder("http://localhost:8080/hello",new RequestParameter("id","1234312"),new RequestParameter("token","9474895"),new RequestParameter("khiem","7483974"));
        System.out.println(rb.getRequest());
        CompletableFuture cf = HttpUtils.sendGETRequest("http://localhost:8080/hello").thenAccept((res)->{System.out.println(res);});
        cf.join();
        for (int i = 0; i < 10 ;i++) {
            CompletableFuture cf2 = HttpUtils.sendGETRequest("https://example.com/").thenAccept((res)->{System.out.println(i + res);});
            cf2.join();

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
