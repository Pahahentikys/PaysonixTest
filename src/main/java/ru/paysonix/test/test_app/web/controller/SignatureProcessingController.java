package ru.paysonix.test.test_app.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paysonix.test.test_app.common.SignatureProcessingState;
import ru.paysonix.test.test_app.web.dto.SignatureProcessRequestDTO;
import ru.paysonix.test.test_app.web.dto.SignatureProcessResponseDTO;
import ru.paysonix.test.test_app.web.dto.SignatureResponseDTO;
import ru.paysonix.test.test_app.web.service.WebSignatureProcessingService;

import java.util.Arrays;

@RestController
@RequestMapping(value = "api/v1/signature")
@RequiredArgsConstructor
public class SignatureProcessingController {
    private final WebSignatureProcessingService webSignatureProcessingService;

    @PostMapping(value = "/{operationId}")
    public ResponseEntity<SignatureProcessResponseDTO> makeSignature(@PathVariable("operationId") Long id,
                                                                     @RequestBody SignatureProcessRequestDTO requestDTO) {
        var signature = webSignatureProcessingService.processSignatureEncodedAsBase64(requestDTO.getForm());

        return ResponseEntity.ok(SignatureProcessResponseDTO.builder()
                .status(SignatureProcessingState.SUCCESS.getName())
                .result(Arrays.asList(SignatureResponseDTO.builder()
                        .signature(signature)
                        .build()))
                .build());
    }

}
