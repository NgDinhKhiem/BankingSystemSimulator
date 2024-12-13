package project.cs3360.response.object;

import project.cs3360.object.TypeHandler;
import project.cs3360.utils.JSONBuilder;

import java.lang.reflect.Field;

public class TransactionData {
    private final String transactionID;
    private final String fromID;
    private final String toID;
    private final double amount;
    private final String time;
    private String state; //SUCCESS/PENDING/FAIL
    public TransactionData(String transactionID, String fromID, String toID, double amount, String time, String state1) {
        this.transactionID = transactionID;
        this.fromID = fromID;
        this.toID = toID;
        this.amount = amount;
        this.time = time;
        state = state1;
    }

    public TransactionData() {
        this.transactionID = "";
        this.fromID = "";
        this.toID = "";
        this.amount = 0;
        this.time = "";
        state = "NULL";
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getFromID() {
        return fromID;
    }

    public String getToID() {
        return toID;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public String getSTATE() {
        return state;
    }
    public void setState(String STATE) {
        this.state = STATE;
    }

    public static TransactionData fromString(String string) {
        JSONBuilder jsonBuilder = JSONBuilder.fromJson(string);
        return new TransactionData(
                jsonBuilder.getObject("transactionID"),
                jsonBuilder.getObject("fromID"),
                jsonBuilder.getObject("toID"),
                TypeHandler.getAdapter(Double.class).convert(jsonBuilder.getObject("amount")),
                jsonBuilder.getObject("time"),
                jsonBuilder.getObject("state")
        );
    }

    public static TransactionData[] fromArrayString(String str) {
        //str = str.substring(1, str.length()-1);
        TransactionData[] data;
        String[] arr = parseJson(str);
        data = new TransactionData[arr.length];
        int i = 0;
        for(String entry:arr){
            TransactionData transactionData = TransactionData.fromString(entry);
            data[i]=transactionData;
            i++;
        }
        return data;
    }

    public static String convertFromArray(TransactionData[] transactions){
        StringBuilder rs = new StringBuilder();
        rs.append("{");
        for(int i = 0;i<transactions.length;i++){
            rs.append(transactions[i].toString());
            if(i!=transactions.length-1){
                rs.append(",");
            }
        }
        rs.append("}");
        return rs.toString();
    }

    public static String[] parseJson(String json) {
        // Trim surrounding braces
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        }

        // Parse key-value pairs considering nested structures
        StringBuilder currentPair = new StringBuilder();
        boolean inQuotes = false;
        int nestedLevel = 0; // Tracks nesting of objects/arrays
        java.util.List<String> pairs = new java.util.ArrayList<>();

        for (char c : json.toCharArray()) {
            if (c == '"' && (currentPair.length() == 0 || currentPair.charAt(currentPair.length() - 1) != '\\')) {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            }

            if (!inQuotes) {
                if (c == '{' || c == '[') nestedLevel++;
                if (c == '}' || c == ']') nestedLevel--;
            }

            // Add current character to the current pair
            currentPair.append(c);

            // Split key-value pairs at top-level commas
            if (nestedLevel == 0 && c == ',' && !inQuotes) {
                pairs.add(currentPair.substring(0, currentPair.length() - 1).trim());
                currentPair.setLength(0); // Clear the current pair buffer
            }
        }

        // Add the last pair if it exists
        if (currentPair.length() > 0) {
            pairs.add(currentPair.toString().trim());
        }

        return pairs.toArray(new String[0]);
    }

    @Override
    public String toString() {
        JSONBuilder jsonBuilder = new JSONBuilder();
        for (Field field : TransactionData.class.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldValue = null;
            try {
                fieldValue = field.get(this).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (fieldValue != null) {
                try {
                    jsonBuilder.addValue(field.getName(), field.get(this).toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            field.setAccessible(false);
        }
        return jsonBuilder.build();
    }
}
