package ru.paysonix.test.test_app.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.paysonix.test.test_app.service.HashingService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class HmacSha256HashingServiceImpl implements HashingService {

    private static final String HMAC_SHA_256 = "HmacSHA256";
    private static final String HASHING_KEY = "hmackKey";

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    @Override
    public @NonNull String makeHash(@NonNull String inputString) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(HASHING_KEY.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA_256);
            mac.init(secretKeySpec);
            return bytesToHex(mac.doFinal(inputString.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
