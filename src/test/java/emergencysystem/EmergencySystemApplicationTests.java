package emergencysystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmergencySystemApplicationTests {

	private static final Logger logger = LogManager.getLogger(EmergencySystemApplicationTests.class);

	@Test
	void shouldLogMyMessage() {

		String myMessage = "You're a Wizard, Harry!";

		logger.debug(myMessage);
	}
}
