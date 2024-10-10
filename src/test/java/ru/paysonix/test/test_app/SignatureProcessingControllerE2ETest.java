package ru.paysonix.test.test_app;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import ru.paysonix.test.test_app.common.SignatureProcessingState;
import ru.paysonix.test.test_app.web.dto.FormFiledDTO;
import ru.paysonix.test.test_app.web.dto.SignatureProcessRequestDTO;

import java.util.Base64;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.paysonix.test.test_app.common.Constants.CUSTOM_TOKEN_HEADER;

@DisplayName("E2E тест контроллера процессинга хэш-суммы")
@RequiredArgsConstructor
public class SignatureProcessingControllerE2ETest extends TestAppApplicationTests {
    private final int operationId = 1;

    @Value("${test-app.header.token}")
    private String headerForTest;

    @Test
    @DisplayName("POST запрос на получения хэш-суммы формы")
    void makeSignature200() throws Exception {
        String expectedHmacSha256EncodedAsBase64 = "NTQwZjZiZTNiNGZiOTg3YTkzMjNkOGFlYTBiZDY5NjQyYmIxZDkyZDYwYzNhODliYzllNTY1MDQ0NGZmYjhiNg==";

        mockMvc.perform(post("/api/v1/signature/{operationId}", operationId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(makeSignatureProcessRequestDTO()))
                        .header(CUSTOM_TOKEN_HEADER, Base64.getEncoder().encodeToString(headerForTest.getBytes(UTF_8))))
                .andExpect(jsonPath("$.status", is(SignatureProcessingState.SUCCESS.getName())))
                .andExpect(jsonPath("$.result.[0].signature", is(expectedHmacSha256EncodedAsBase64)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("POST запрос на получения хэш-суммы формы с ошибкой авторизации")
    void makeSignature403() throws Exception {
        String wrongHeader = "wrongHeader";

        mockMvc.perform(post("/api/v1/signature/{operationId}", operationId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(makeSignatureProcessRequestDTO()))
                        .header(CUSTOM_TOKEN_HEADER, Base64.getEncoder().encodeToString(wrongHeader.getBytes(UTF_8))))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    private SignatureProcessRequestDTO makeSignatureProcessRequestDTO() {
        return new SignatureProcessRequestDTO(List.of(
                new FormFiledDTO("testField1", "testValue2"),
                new FormFiledDTO("testField2", "testValue2")));
    }
}
