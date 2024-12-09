package com.pf.mas.model.util.encryptor;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

public class CryptoUtils {
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        (new SecureRandom()).nextBytes(nonce);
        return nonce;
    }

    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    public static SecretKey getAESKey(char[] password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secret;
    }

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            result.append(String.format("%02x", b));
        }

        return result.toString();
    }

    public static String hexWithBlockSize(byte[] bytes, int blockSize) {
        String hex = hex(bytes);
        blockSize *= 2;
        List<String> result = new ArrayList<String>();
        byte index = 0;

        while (index < hex.length()) {
            result.add(hex.substring(index, Math.min(index + blockSize, hex.length())));
        }

        int var10000 = index + blockSize;
        return result.toString();
    }
}
