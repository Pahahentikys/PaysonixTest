package ru.paysonix.test.test_app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignatureProcessRequestDTO {
    private List<FormFiledDTO> form;
}
