package project.cs3360.utils;

import java.util.concurrent.CompletableFuture;

public class myThread implements Runnable{
    private final int index;
    public myThread(int index) {
        this.index = index;
    }
    @Override
    public void run(){
        long startTime = System.currentTimeMillis();
        CompletableFuture<Void> cf = HttpUtils.sendGETRequest("https://example.com/")
                .thenAccept(res -> {
                    long executionTime = System.currentTimeMillis() - startTime;
                    System.out.println("Request: " + index + ": " + res);
                    System.out.println("Request: " + index + " completed in: " + executionTime);
                });
        cf.join();
    }
}
