package com.pf.mas.model.util.encryptor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encryptor {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8;

    public static String encrypt(byte[] pText, String key) throws Exception {
        byte[] salt = CryptoUtils.getRandomNonce(16);
        byte[] iv = CryptoUtils.getRandomNonce(12);
        SecretKey aesKey = CryptoUtils.getAESKey(key.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(1, aesKey, new GCMParameterSpec(128, iv));
        byte[] cipherText = cipher.doFinal(pText);
        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv).put(salt).put(cipherText).array();
        return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
    }

    public static String decrypt(String cText, String key) throws Exception {
        byte[] decode = Base64.getDecoder().decode(cText);
        ByteBuffer bb = ByteBuffer.wrap(decode);
        byte[] iv = new byte[12];
        bb.get(iv);
        byte[] salt = new byte[16];
        bb.get(salt);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);
        SecretKey aesKey = CryptoUtils.getAESKey(key.toCharArray(), salt);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(2, aesKey, new GCMParameterSpec(128, iv));
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText, UTF_8);
    }

    static {
        UTF_8 = StandardCharsets.UTF_8;
    }
}
