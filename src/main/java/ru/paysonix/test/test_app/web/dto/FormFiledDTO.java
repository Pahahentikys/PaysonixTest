package ru.paysonix.test.test_app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFiledDTO {
    private String formFieldName;

    private String formFieldValue;
}
