package ru.paysonix.test.test_app.web.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.paysonix.test.test_app.service.HashingService;
import ru.paysonix.test.test_app.web.dto.FormFiledDTO;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class WebSignatureProcessingService {
    private final HashingService hashingService;

    @Value("${test-app.header.token}")
    private String storedHeaderToken;

    @Value("${test-app.form.secret.key}")
    private String storedFormSecretKey;


    public boolean hasValidTokenInHeader(@NonNull String tokenHeader) {
        var decodedTokenFromHeader = new String(Base64.getDecoder().decode(tokenHeader.getBytes(StandardCharsets.UTF_8)));

        return decodedTokenFromHeader.equals(storedHeaderToken);
    }

    public String processSignatureEncodedAsBase64(List<FormFiledDTO> form) {
        return encodeAsBase64(hashingService.makeHash(makeFormattedString(form), storedFormSecretKey));
    }

    private String encodeAsBase64(String signature) {
        return Base64.getEncoder().encodeToString(signature.getBytes(StandardCharsets.UTF_8));
    }

    private String makeFormattedString(List<FormFiledDTO> formFields) {
        var sortedFormParams = formFields.stream()
                .sorted(comparing(FormFiledDTO::getFormFieldName))
                .toList();

        return sortedFormParams.stream()
                .map(ff -> "%s=%s".formatted(ff.getFormFieldName(), ff.getFormFieldValue()))
                .collect(Collectors.joining("&"));
    }
}
