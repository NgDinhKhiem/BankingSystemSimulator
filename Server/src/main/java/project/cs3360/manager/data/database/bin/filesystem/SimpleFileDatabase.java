package project.cs3360.manager.data.database.bin.filesystem;

import project.cs3360.Server;
import project.cs3360.manager.data.database.DataSourceHandler;
import project.cs3360.object.TypeHandler;
import project.cs3360.response.*;
import project.cs3360.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleFileDatabase implements DataSourceHandler {
    private final Server server;
    private static final String ACCOUNT_BALANCE_FILE = "AccountBalance.txt";
    private static final String ACCOUNT_AUTH_FILE = "AccountAuth.txt";
    private static final String ACCOUNT_INFO_FILE = "AccountInfo.txt";
    private static final String TRANSACTION_FILE = "Transaction.txt";

    public SimpleFileDatabase(Server server) {
        this.server = server;
        initFile(ACCOUNT_BALANCE_FILE);
        initFile(ACCOUNT_AUTH_FILE);
        initFile(ACCOUNT_INFO_FILE);
        initFile(TRANSACTION_FILE);
    }

    private void initFile(String filePath) {
        File file = new File(filePath);

        // Check if file exists
        if (!file.exists()) {
            try {
                // Create the file
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
            }
        }
    }

    private synchronized void writeToFile(String fileName, String data){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized List<String> readFromFile(String fileName){
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    @Override
    public AccountBalanceResponse getAccountBalance(String ID) {
        List<String> balances = readFromFile(ACCOUNT_BALANCE_FILE);
        for (String balance : balances) {
            String[] details = balance.split(",");
            if (details[0].equals(ID)) {
                return new AccountBalanceResponse(true, TypeHandler.getAdapter(Integer.class).convert(balance));
            }
        }
        return new AccountBalanceResponse(false, -1);
    }

    @Override
    public AccountInformationResponse getAccountInformation(String ID) {
        List<String> users = readFromFile(ACCOUNT_INFO_FILE);
        for (String user : users) {
            String[] details = user.split(",");
            if (details[0].equals(ID)) {
                return new AccountInformationResponse(
                        true,
                        details[0],
                        details[1],
                        details[2],
                        details[3],
                        details[4]
                        );
            }
        }
        return new AccountInformationResponse(false,"","","","","");
    }

    @Override
    public AuthenticationResponse getAuthenticationResponse(String ID, String password) {
        List<String> auths = readFromFile(ACCOUNT_AUTH_FILE);
        for (String auth : auths) {
            String[] details = auth.split(",");
            if (details[0].equals(ID) && details[1].equals(password)) {
                return new AuthenticationResponse(true, this.server.getDataManager().createToken(ID));
            }
        }
        return new AuthenticationResponse(false, "");
    }

    @Override
    public RegistrationResponse getRegistrationResponse(String firstName, String lastName, String phoneNumber, String email, String password) {
        String ID = Utils.generateAccountID();
        String defaultBalance = "0.0";

        // Store account balance
        writeToFile(ACCOUNT_BALANCE_FILE, ID + "," + defaultBalance);

        // Store account authentication
        writeToFile(ACCOUNT_AUTH_FILE, ID + "," + password);

        // Store account information
        writeToFile(ACCOUNT_INFO_FILE, ID + "," + firstName + "," + lastName + "," + phoneNumber + "," + email);

        return new RegistrationResponse(true, ID);
    }

    @Override
    public AccountVerificationResponse getAccountVerificationResponse(String receiverID) {
        List<String> users = readFromFile(ACCOUNT_INFO_FILE);
        for (String user : users) {
            String[] details = user.split(",");
            if (details[0].equals(receiverID)) {
                return new AccountVerificationResponse(true,  details[1] + " " + details[2]);
            }
        }
        return new AccountVerificationResponse(false, "");
    }

    @Override
    public SendingMoneyResponse getSendingMoneyResponse(String ID) {
        return new SendingMoneyResponse(false);
    }
}
