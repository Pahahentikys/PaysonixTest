package ru.paysonix.test.test_app.web.service;

import ru.paysonix.test.test_app.web.dto.FormFiledDTO;

import java.util.List;

public interface WebSignatureProcessingService {
    String processSignatureEncodedAsBase64(List<FormFiledDTO> form);
}
