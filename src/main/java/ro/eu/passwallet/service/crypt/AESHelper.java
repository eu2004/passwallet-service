package ro.eu.passwallet.service.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class AESHelper {
    private final String messageDigestAlgorithm;
    private final String secretKeyAlgorithm;

    public AESHelper() {
        this("SHA512", "AES");
    }

    protected AESHelper(String messageDigestAlgorithm, String secretKeyAlgorithm) {
        this.messageDigestAlgorithm = messageDigestAlgorithm;
        this.secretKeyAlgorithm = secretKeyAlgorithm;
    }

    private SecretKeySpec generateKey(String password) {
        try {
            byte[] key = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance(messageDigestAlgorithm);
            byte[] secretKey = getSecretKey(sha.digest(key));
            return new SecretKeySpec(secretKey, secretKeyAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptographyException(e);
        }
    }

    public byte[] encrypt(byte[] strToEncrypt, String secret) {
        try {
            SecretKeySpec secretKey = generateKey(secret);
            Cipher cipher = Cipher.getInstance(secretKeyAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(strToEncrypt);
        } catch (Exception e) {
            throw new CryptographyException(e);
        }
    }

    public byte[] decrypt(byte[] strToDecrypt, String secret) {
        try {
            SecretKeySpec secretKey = generateKey(secret);
            Cipher cipher = Cipher.getInstance(secretKeyAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(strToDecrypt);
        } catch (Exception e) {
            throw new CryptographyException(e);
        }
    }

    protected byte[] getSecretKey(byte[] hashKey) {
        byte[] secretKey = new byte[hashKey.length / 2];
        int j = 0;
        for (int i = 0; i < hashKey.length; i++) {
            if (i % 2 == 0) {
                secretKey[j++] = hashKey[i];
            }
        }
        return secretKey;
    }

}