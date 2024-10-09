package ru.paysonix.test.test_app.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paysonix.test.test_app.common.SignatureProcessingState;
import ru.paysonix.test.test_app.service.HashingService;
import ru.paysonix.test.test_app.web.dto.FormFiledDTO;
import ru.paysonix.test.test_app.web.dto.SignatureProcessRequestDTO;
import ru.paysonix.test.test_app.web.dto.SignatureProcessResponseDTO;
import ru.paysonix.test.test_app.web.dto.SignatureResponseDTO;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping(value = "api/v1/signature")
@RequiredArgsConstructor
public class SignatureProcessingController {
    private final HashingService hashingService;

    @Value("${test-app.header.token}")
    private String storedHeaderToken;

    @Value("${test-app.form.secret.key}")
    private String storedFormSecretKey;

    @PostMapping(value = "/{operationId}/")
    public ResponseEntity<SignatureProcessResponseDTO> makeSignature(@RequestHeader(value = "Token") String tokenHeader,
                                                                     @PathVariable("operationId") Long id,
                                                                     @RequestBody SignatureProcessRequestDTO requestDTO) {
        if (!hasValidTokenInHeader(tokenHeader)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN.value())
                    .body(SignatureProcessResponseDTO.builder()
                            .status(SignatureProcessingState.ERROR.getName())
                            .build());
        }

        var sortedFormParams = requestDTO.getForm().stream()
                .sorted(comparing(FormFiledDTO::getFormFieldName))
                .toList();

        var preparedStringForSignature = sortedFormParams.stream()
                .map(ff -> "%s=%s".formatted(ff.getFormFieldName(), ff.getFormFieldValue()))
                .collect(Collectors.joining("&"));

        var signature = hashingService.makeHash(preparedStringForSignature, storedFormSecretKey);

        return ResponseEntity.ok(SignatureProcessResponseDTO.builder()
                .status(SignatureProcessingState.SUCCESS.getName())
                .result(Arrays.asList(SignatureResponseDTO.builder()
                        .signature(signature)
                        .build()))
                .build());
    }

    private boolean hasValidTokenInHeader(String tokenHeader) {
        var decodedTokenFromHeader = new String(Base64.getDecoder().decode(tokenHeader.getBytes(StandardCharsets.UTF_8)));

        return decodedTokenFromHeader.equals(storedHeaderToken);
    }
}
