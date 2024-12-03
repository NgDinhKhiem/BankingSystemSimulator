package project.cs3360.socketserver.utils;

public class Utils {
    public static String joinString(String... s){
        StringBuilder stringBuilder = new StringBuilder();
        for(String str:s){
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }
}
