package project.cs3360.utils;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class Utils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRDKey(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            otp.append(CHARACTERS.charAt(index));
        }

        return otp.toString();
    }

    public static String joinString(String... lines){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s:lines) stringBuilder.append(s);
        return stringBuilder.toString();
    }

    public static String generateAccountID() {
        int min = 10000000; // Smallest 8-digit number
        int max = 99999999; // Largest 8-digit number
        return String.valueOf(ThreadLocalRandom.current().nextInt(max - min + 1) + min);
    }

    public static String generateTransactionID() {
        return UUID.randomUUID().toString();
    }
}
