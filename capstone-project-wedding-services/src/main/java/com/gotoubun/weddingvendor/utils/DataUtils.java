package com.gotoubun.weddingvendor.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.Locale;

public class DataUtils {
    public static String hmacSHA512(byte[] inputBytes, String algorithm){
        String hashvalue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();
            hashvalue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        }catch(Exception e){
            e.printStackTrace();
        }return  hashvalue;
    }
}
