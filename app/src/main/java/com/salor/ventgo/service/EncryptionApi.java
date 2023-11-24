package com.salor.ventgo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class EncryptionApi {

    public static String SCREET_KEY = "d53a685c931f9880105b853d5bf090ab";
    public static long token_time;
    public static final String KEY_TOKEN_CODE = "token_code";
    public static final String KEY_TOKEN_TIME = "token_time";

    public static String userAgent = System.getProperty("http.agent");

    public EncryptionApi() {
    }

    private static long getCurrentTime(){
        Date date = new Date();

        return date.getTime();
    }

    private static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String crypt() {
        token_time = getCurrentTime();

        String token_kode=SCREET_KEY+token_time+userAgent;

        if (token_kode == null || token_kode.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(token_kode.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return hexString.toString();
    }
}
