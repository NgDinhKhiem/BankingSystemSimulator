package project.cs3360.manager.data.database.bin.filesystem;

import project.cs3360.Server;
import project.cs3360.manager.data.database.DataSourceHandler;
import project.cs3360.manager.data.notification.noti.TransactionNotification;
import project.cs3360.response.*;
import project.cs3360.response.object.TransactionData;
import project.cs3360.utils.Utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SimpleFileDatabase implements DataSourceHandler {
    private static final String ADMIN = "0000";

    private final Server server;
    private static final String ACCOUNT_BALANCE_FILE = "AccountBalance.txt";
    private static final String ACCOUNT_AUTH_FILE = "AccountAuth.txt";
    private static final String ACCOUNT_INFO_FILE = "AccountInfo.txt";
    private static final String TRANSACTION_FILE = "Transaction.txt";

    // Set up a transaction queue
    private final Queue<String> pendingTransactionsQueue = new ConcurrentLinkedQueue<>();

    // This method processes transactions in the queue.
    private void processTransactionQueue() {
        new Thread(() -> {
            while (true) {
                String transactionID = pendingTransactionsQueue.poll();
                if (transactionID != null) {
                    try {
                        TransactionData transactionData = fetchTransactionFromDatabase(transactionID);
                        if (transactionData != null) {
                            synchronized (this) { // Synchronize to ensure atomic checks
                                if (validateTransaction(transactionData)) {
                                    executeTransaction(transactionData);
                                    transactionData.setState("COMPLETED");
                                    saveTransactionIDToDatabase(transactionData);
                                } else {
                                    transactionData.setState("FAIL");
                                    saveTransactionIDToDatabase(transactionData);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error processing transaction ID: " + transactionID + " - " + e.getMessage());
                    }
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private boolean validateTransaction(TransactionData transactionData) {
        String senderID = transactionData.getFromID();
        String receiverID = transactionData.getToID();
        double amount = transactionData.getAmount();

        if (!accountExists(senderID) || !accountExists(receiverID)) {
            System.err.println("Either sender or receiver does not exist.");
            return false;
        }

        AccountBalanceResponse senderBalanceResponse = getAccountBalance(senderID);
        if (!senderBalanceResponse.isSuccess()) {
            System.err.println("Could not fetch sender balance.");
            return false;
        }

        if (senderBalanceResponse.getBalance() < amount) {
            System.err.println("Sender does not have enough balance.");
            return false;
        }

        return true;
    }

    private boolean accountExists(String accountID) {
        if(accountID.equals(ADMIN)) {
            return true;
        }
        List<String> accounts = readFromFile(ACCOUNT_BALANCE_FILE);
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details[0].equals(accountID)) {
                return true;
            }
        }
        return false;
    }

    /*private double _getAccountBalance(String accountID) {
        List<String> accounts = readFromFile(ACCOUNT_BALANCE_FILE);
        for (String account : accounts) {
            String[] details = account.split(",");
            if (details[0].equals(accountID)) {
                try {
                    return Double.parseDouble(details[1]);
                } catch (NumberFormatException e) {
                    return 0; // Default to 0 on unexpected errors
                }
            }
        }
        return 0; // Default return value
    }*/

    private synchronized void executeTransaction(TransactionData transactionData) {
        String senderID = transactionData.getFromID();
        String receiverID = transactionData.getToID();
        double amount = transactionData.getAmount();

        double senderBalance = getAccountBalance(senderID).getBalance();
        double receiverBalance = getAccountBalance(receiverID).getBalance();

        senderBalance -= amount;
        receiverBalance += amount;

        updateAccountBalance(senderID, senderBalance);
        updateAccountBalance(receiverID, receiverBalance);

        server.getNotificationManager().pushNotification(
                new TransactionNotification("COMPLETED", transactionData.getTransactionID()),
                senderID,
                receiverID
                );

        System.out.println("Transferred " + amount + " from " + senderID + " to " + receiverID);
    }

    public boolean updateAccountBalance(String accountID, double newBalance) {
        if(accountID.equals(ADMIN)) {return true;}
        List<String> accounts = readFromFile(ACCOUNT_BALANCE_FILE);
        boolean accountFound = false;

        for(int i = 0; i < accounts.size(); i++) {
            String account = accounts.get(i);
            String[] accountDetails = account.split(",");

            if (accountDetails.length >= 2 && accountDetails[0].equals(accountID)) {
                accounts.set(i,accountDetails[0] + "," + newBalance);
                accountFound = true;
            }
        }

        if (!accountFound) {
            // Account not found, add a new entry
            accounts.add(accountID + "," + newBalance);
        }

        // Write the updated accounts back to the balance file
        writeToFile(ACCOUNT_BALANCE_FILE, String.join(",\n", accounts));

        return true;
    }

    private TransactionData fetchTransactionFromDatabase(String transactionID) {
        List<String> transactions = readFromFile(TRANSACTION_FILE);
        for (String transaction : transactions) {
            TransactionData transactionData = TransactionData.fromString(transaction);
            if (transactionData.getTransactionID().equals(transactionID)) {
                return transactionData;
            }
        }
        return null;
    }
    public SimpleFileDatabase(Server server) {
        this.server = server;
        initFile(ACCOUNT_BALANCE_FILE);
        initFile(ACCOUNT_AUTH_FILE);
        initFile(ACCOUNT_INFO_FILE);
        initFile(TRANSACTION_FILE);

        processTransactionQueue();
    }

    private void initFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file: " + e.getMessage());
            }
        }
    }

    private synchronized void writeToFile(String fileName, String data){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
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

        if(ID.equals(ADMIN)) {
            return new AccountBalanceResponse(true, Integer.MAX_VALUE);
        }

        List<String> balances = readFromFile(ACCOUNT_BALANCE_FILE);

        for (String balance : balances) {
            String[] details = balance.split(",");
            if (details[0].equals(ID)) {
                return new AccountBalanceResponse(true, Double.parseDouble(details[1]));
            }
        }

        // If account doesn't exist in the balance file, create a new one with balance 0
        double defaultBalance = 0.0;
        updateAccountBalance(ID, defaultBalance);

        return new AccountBalanceResponse(true, defaultBalance);
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
        String defaultBalance = "0";

        // Store account authentication
        List<String> auth_accounts = readFromFile(ACCOUNT_AUTH_FILE);
        auth_accounts.add(ID + "," + password);
        writeToFile(ACCOUNT_AUTH_FILE, String.join(",\n", auth_accounts));

        // Store account information
        List<String> info_accounts = readFromFile(ACCOUNT_INFO_FILE);
        info_accounts.add(ID + "," + firstName + "," + lastName + "," + phoneNumber + "," + email);
        writeToFile(ACCOUNT_INFO_FILE, String.join(",\n", info_accounts));

        // Initialize account balance
        updateAccountBalance(ID, Double.parseDouble(defaultBalance));

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
    public SendingMoneyResponse getSendingMoneyResponse(String ID, String receiverID, double amount) {
        String transactionID = Utils.generateTransactionID();
        String transactionTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
        TransactionData transactionData = new TransactionData(
                transactionID, ID, receiverID, amount, transactionTime, "PENDING"
        );

        saveTransactionIDToDatabase(transactionData);

        pendingTransactionsQueue.offer(transactionID);

        return new SendingMoneyResponse(true, transactionID);
    }

    @Override
    public TransactionIDResponse getTransactionDataResponse(String transactionID) {
        List<String> transactions = readFromFile(TRANSACTION_FILE);

        for (String transaction : transactions) {
            TransactionData transactionData = TransactionData.fromString(transaction);
            //System.out.println(transactionData);//state
            //System.out.println(transactionData.toString());
            if (transactionData.getTransactionID().equals(transactionID)) {
                //return new TransactionIDResponse(new TransactionData());
                return new TransactionIDResponse(transactionData);
            }
        }

        return new TransactionIDResponse(new TransactionData());
    }

    @Override
    public TransactionListResponse getTransactionInformationResponse(String ID, int startIndex, int endIndex, String startDate, String endDate) {
        List<String> transactions = readFromFile(TRANSACTION_FILE);
        List<TransactionData> filteredTransactions = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

        for (String transaction : transactions) {
            TransactionData transactionData = TransactionData.fromString(transaction);

            if (transactionData.getFromID().equals(ID) || transactionData.getToID().equals(ID)) {
                if (!(startDate.equals("-1") || startDate.equals("*") || endDate.equals("-1") || endDate.equals("*"))) {
                    if (!startDate.isEmpty() && !endDate.isEmpty()) {
                        try {
                            Date transactionDate = sdf.parse(transactionData.getTime());
                            Date start = sdf.parse(startDate);
                            Date end = sdf.parse(endDate);

                            if (transactionDate.before(start) || transactionDate.after(end)) {
                                continue;
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException("Error parsing date: " + e.getMessage());
                        }
                    }
                }
                filteredTransactions.add(transactionData);
            }
        }

        filteredTransactions.sort((t1, t2) -> {
            try {
                Date date1 = sdf.parse(t1.getTime());
                Date date2 = sdf.parse(t2.getTime());
                return date2.compareTo(date1);
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing transaction dates: " + e.getMessage());
            }
        });

        if (startIndex != -1 && endIndex != -1) {
            if (startIndex >= 0 && endIndex < filteredTransactions.size() && startIndex <= endIndex) {
                filteredTransactions = filteredTransactions.subList(startIndex, endIndex + 1);
            } else if (startIndex >= 0 && startIndex < filteredTransactions.size()) {
                filteredTransactions = filteredTransactions.subList(startIndex, filteredTransactions.size());
            } else {
                filteredTransactions.clear();
            }
        }

        return new TransactionListResponse(filteredTransactions.toArray(new TransactionData[0]));
    }


    private synchronized void saveTransactionIDToDatabase(TransactionData transactionData) {
        List<String> transactions = readFromFile(TRANSACTION_FILE);

        boolean transactionExists = false;
        for (int i = 0; i < transactions.size(); i++) {
            TransactionData existingTransaction = TransactionData.fromString(transactions.get(i));
            if (existingTransaction.getTransactionID().equals(transactionData.getTransactionID())) {
                transactions.set(i, transactionData.toString());
                transactionExists = true;
                break;
            }
        }

        if (!transactionExists) {
            transactions.add(transactionData.toString());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE, false))) {
            for (String transaction : transactions) {
                writer.write(transaction);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to save transaction: " + e.getMessage());
        }
    }
}
