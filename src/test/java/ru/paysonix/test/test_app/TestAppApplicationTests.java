package ru.paysonix.test.test_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = TestAppApplication.class)
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
class TestAppApplicationTests {
	public static final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	protected MockMvc mockMvc;

}
