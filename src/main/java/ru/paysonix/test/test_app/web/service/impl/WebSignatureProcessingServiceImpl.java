package ru.paysonix.test.test_app.web.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.paysonix.test.test_app.service.HashingService;
import ru.paysonix.test.test_app.web.dto.FormFiledDTO;
import ru.paysonix.test.test_app.web.service.WebSignatureProcessingService;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class WebSignatureProcessingServiceImpl implements WebSignatureProcessingService {
    private final HashingService hashingService;

    @Value("${test-app.form.secret.key}")
    private String storedFormSecretKey;

    @Override
    public String processSignatureEncodedAsBase64(List<FormFiledDTO> form) {
        return encodeAsBase64(hashingService.makeHash(makeFormattedString(form), storedFormSecretKey));
    }

    private String encodeAsBase64(String signature) {
        return Base64.getEncoder().encodeToString(signature.getBytes(UTF_8));
    }

    private String makeFormattedString(List<FormFiledDTO> formFields) {
        return formFields.stream()
                .sorted(comparing(FormFiledDTO::getFormFieldName))
                .map(ff -> "%s=%s".formatted(ff.getFormFieldName(), ff.getFormFieldValue()))
                .collect(Collectors.joining("&"));
    }
}
