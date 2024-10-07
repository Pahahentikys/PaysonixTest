package ru.paysonix.test.test_app.service.impl;

import lombok.NonNull;
import ru.paysonix.test.test_app.service.HashingService;

public class HmacSha256HashingServiceImpl implements HashingService {

    @Override
    public @NonNull String makeHash(@NonNull String inputString) {
        return "";
    }
}
