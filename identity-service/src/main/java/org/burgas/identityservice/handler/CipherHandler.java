package org.burgas.identityservice.handler;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static javax.crypto.Cipher.*;

@SuppressWarnings("unused")
@Component
public class CipherHandler {

    private final SecretKey secretKey;

    {
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unused")
    public String encode(String string)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        Cipher cipher = getInstance("AES");
        cipher.init(ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(string.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    @SuppressWarnings("unused")
    public String decode(String string)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = getInstance("AES");
        cipher.init(DECRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(string.getBytes(StandardCharsets.UTF_8)));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
