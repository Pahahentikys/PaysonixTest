package ru.paysonix.test.test_app.service.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.paysonix.test.test_app.service.HashingService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class HmacSha256HashingServiceImpl implements HashingService {

    private static final String HMAC_SHA_256 = "HmacSHA256";

    @Override
    public @NonNull String makeHash(@NonNull String inputString, @NonNull String secretKey) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(UTF_8), HMAC_SHA_256);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA_256);
            mac.init(secretKeySpec);
            return HexFormat.of().formatHex(mac.doFinal(inputString.getBytes(UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("В процессе получения хэша возникла ошибка", e);
        }
    }
}
