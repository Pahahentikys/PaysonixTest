package ru.paysonix.test.test_app.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.paysonix.test.test_app.web.dto.SignatureProcessRequestDTO;
import ru.paysonix.test.test_app.web.dto.SignatureProcessResponseDTO;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/signature")
public class SignatureProcessingController {
    @PostMapping(value = "/{operationId}/")
    public ResponseEntity<SignatureProcessResponseDTO> makeSignature(@PathVariable("operationId") Long id, @RequestBody List<SignatureProcessRequestDTO> formParams) {
        return ResponseEntity.ok().build();
    }
}
