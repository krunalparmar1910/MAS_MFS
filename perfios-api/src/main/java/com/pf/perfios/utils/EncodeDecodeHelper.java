package com.pf.perfios.utils;

import com.pf.common.exception.MasRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
@Component
public class EncodeDecodeHelper {
    private static final String ENCRYPTION_ALGO = "SHA256withRSA/PSS";

    public static String getSHA256Hex(String data) {
        return DigestUtils.sha256Hex(data);
    }

    public static String encryptWithRSA(String raw, PrivateKey rsaPrivateKey, PublicKey rsaPublicKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Signature privateSignature = Signature.getInstance(ENCRYPTION_ALGO);
            privateSignature.initSign(rsaPrivateKey);
            privateSignature.update(raw.getBytes(StandardCharsets.UTF_8));
            byte[] signature = privateSignature.sign();
            byte[] encoded = Hex.encode(signature);
            return new String(encoded);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error("Error generating signature: " + e.getMessage());
            throw new MasRuntimeException(HttpStatus.BAD_GATEWAY, "Error while encryption of signature.");
        }
    }

    public static boolean verifySignature(String raw, String signature, PublicKey publicKey) {
        try {
            Signature publicSignature = Signature.getInstance(ENCRYPTION_ALGO);
            publicSignature.initVerify(publicKey);
            publicSignature.update(raw.getBytes(StandardCharsets.UTF_8));
            return publicSignature.verify(hexToBytes(signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error("Error verifying signature: " + e.getMessage());
            return false;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexString.append(String.format("%02X", b));
        }
        return hexString.toString();
    }

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static KeyPair loadKeyPair(String privateKeyPath, String publicKeyPath) {
        PrivateKey privateKey = loadPrivateKey(privateKeyPath);
        PublicKey publicKey = loadPublicKey(publicKeyPath);

        return new KeyPair(publicKey, privateKey);
    }

    public static PublicKey loadPublicKey(String filePath) {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream));
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return converter.getPublicKey(publicKeyInfo);
        } catch (Exception e) {
            throw new MasRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Not able to read public key. {%s}", e));
        }
    }

    public static PrivateKey loadPrivateKey(String filePath) {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream));
            PEMKeyPair pemKeyPair = (PEMKeyPair) pemParser.readObject();
            byte[] encoded = pemKeyPair.getPrivateKeyInfo().getEncoded();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));

        } catch (Exception e) {
            throw new MasRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("Not able to read private key, {%s}", e));
        }
    }

    public static String uriEncode(final CharSequence input) {
        final StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            final char ch = input.charAt(i);
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9') || ch == '_' || ch == '-' || ch == '~' || ch == '.' || ch == '/') {
                result.append(ch);
            } else {
                result.append(Integer.toHexString(ch));
            }
        }
        return result.toString();
    }

}