package ru.paysonix.test.test_app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignatureProcessResponseDTO {
    private String status;

    public List<SignatureResponseDTO> result;
}
