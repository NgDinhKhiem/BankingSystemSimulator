import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class UserDB {
    public static HashMap<String, String> userDB = new HashMap<>();
//    public static void addUser(String username, String password){
//        userDB.put(username,password);
//    }
    public static void addUser(String username, String password, String email) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/xuankhai/Downloads/BankingSystemSimulator-master(1) (2)/BankingSystemSimulator-master/Client/src/UIapp/src/UserDB/UserDB.txt",true));
        writer.write(username + " " + password + " " + email);
        writer.newLine();
        writer.close();

    }

}
