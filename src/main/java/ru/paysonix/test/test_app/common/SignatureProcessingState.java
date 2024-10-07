package ru.paysonix.test.test_app.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SignatureProcessingState {
    SUCCESS("success"),
    ERROR("error");

    private final String name;
}
