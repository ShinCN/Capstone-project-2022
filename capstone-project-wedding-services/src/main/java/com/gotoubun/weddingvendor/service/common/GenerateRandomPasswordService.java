package com.gotoubun.weddingvendor.service.common;

import java.security.SecureRandom;

public class GenerateRandomPasswordService {
    public static class GenerateRandomPassword {
        public static String generateRandomString(int len) {
            // ASCII range – alphanumeric (0-9, a-z, A-Z)
            final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder();

            // each iteration of the loop randomly chooses a character from the given
            // ASCII range and appends it to the `StringBuilder` instance

            for (int i = 0; i < len; i++) {
                int randomIndex = random.nextInt(chars.length());
                sb.append(chars.charAt(randomIndex));
            }

            return sb.toString();
        }
    }

}

