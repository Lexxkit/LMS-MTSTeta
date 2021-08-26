package com.alekseenko.lms;

import com.alekseenko.lms.event.RegistrationListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LmsApplicationTests {

	@MockBean
	RegistrationListener registrationListener;

	@Test
	void contextLoads() {
	}

}
