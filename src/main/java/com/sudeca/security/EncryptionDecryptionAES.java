package com.sudeca.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * * Author: Francisco Hernandez
 **/
public class EncryptionDecryptionAES {
    static Cipher cipher;
    /*
        create key
        If we need to generate a new key use a KeyGenerator
        If we have existing plaintext key use a SecretKeyFactory
       */
    KeyGenerator keyGenerator;
        //keyGenerator.init(128); // block size is 128bits
        public SecretKey secretKey;

    public EncryptionDecryptionAES() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128); // block size is 128bits
        this.secretKey = keyGenerator.generateKey();
        cipher = Cipher.getInstance("AES"); //SunJCE provider AES algorithm, mode(optional) and padding schema(optional)
    }

    public static String encrypt(String value, SecretKey secretKey)
            throws Exception {
        byte[] valueByte = value.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(valueByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }

}
