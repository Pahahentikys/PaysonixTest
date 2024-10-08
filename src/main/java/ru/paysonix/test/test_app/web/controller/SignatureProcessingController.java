package ru.paysonix.test.test_app.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paysonix.test.test_app.common.SignatureProcessingState;
import ru.paysonix.test.test_app.service.HashingService;
import ru.paysonix.test.test_app.web.dto.SignatureProcessRequestDTO;
import ru.paysonix.test.test_app.web.dto.SignatureProcessResponseDTO;
import ru.paysonix.test.test_app.web.dto.SignatureResponseDTO;

import java.util.List;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping(value = "api/v1/signature")
@RequiredArgsConstructor
public class SignatureProcessingController {
    private final HashingService hashingService;

    @PostMapping(value = "/{operationId}/")
    public ResponseEntity<SignatureProcessResponseDTO> makeSignature(@PathVariable("operationId") Long id, @RequestBody List<SignatureProcessRequestDTO> formParams) {
        var sortedFormParams = formParams.stream()
                .sorted(comparing(SignatureProcessRequestDTO::getFormFieldName))
                .toList();

        var signaturesResponse = sortedFormParams.stream()
                .map(fp -> {
                    var hash = hashingService.makeHash("%s=%s".formatted(fp.getFormFieldName(), fp.getFormFieldValue()));

                    return SignatureResponseDTO.builder()
                            .signature(hash)
                            .build();
                }).toList();


        return ResponseEntity.ok(SignatureProcessResponseDTO.builder()
                .status(SignatureProcessingState.SUCCESS.getName())
                .result(signaturesResponse)
                .build());
    }
}
