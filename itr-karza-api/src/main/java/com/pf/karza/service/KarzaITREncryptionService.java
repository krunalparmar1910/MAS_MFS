package com.pf.karza.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class KarzaITREncryptionService {
    private final TextEncryptor textEncryptor;

    public KarzaITREncryptionService(@Value("${itr.encryption.password}") String password, @Value("${itr.encryption.salt}") String salt) {
        textEncryptor = Encryptors.text(password, salt);
    }

    public String encrypt(String text) {
        return textEncryptor.encrypt(text);
    }

    public String decrypt(String text) {
        return textEncryptor.decrypt(text);
    }
}
