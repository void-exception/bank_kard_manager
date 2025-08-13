package com.example.bankcards.service.card;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Сервис для шифрования и расшифровки данных с использованием AES.
 */
@Service
public class EncodeService {
    @Value("${encryption.key}")
    private String encryptKey;

    public String encryption(String number) throws Exception {
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] numberBytes = cipher.doFinal(number.getBytes());
        return Base64.getEncoder().encodeToString(numberBytes);
    }

    public String decryption(String byteNumber) {
        try {

            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedNumber = cipher.doFinal(Base64.getDecoder().decode(byteNumber));
            return new String(decryptedNumber, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка расшифровки", e);
        }
    }
}
