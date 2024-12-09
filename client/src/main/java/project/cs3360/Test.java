package project.cs3360;

import project.cs3360.response.object.TransactionData;

public class Test {
    public static void main(String[] args){
        TransactionData transactionData = new TransactionData();
        TransactionData[] transactionData1 = new TransactionData[3];
        transactionData1[0] = transactionData;
        transactionData1[1] = transactionData;
        transactionData1[2] = transactionData;
        System.out.println(TransactionData.convertFromArray(transactionData1));
        for(TransactionData data:TransactionData.fromArrayString(TransactionData.convertFromArray(transactionData1))){
            System.out.println(data);
        }
    }
}
