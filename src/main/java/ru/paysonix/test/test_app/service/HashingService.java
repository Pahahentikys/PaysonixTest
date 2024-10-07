package ru.paysonix.test.test_app.service;

import lombok.NonNull;

public interface HashingService {

    @NonNull
    String makeHash(@NonNull String inputString);
}
