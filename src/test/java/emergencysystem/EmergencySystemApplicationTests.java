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
		String myMessage = "Greetings!";
		System.out.println(myMessage);

		logger.trace("The user has just been greeted!");
		logger.debug("The user has just been greeted!");
		logger.info("The user has just been greeted!");
		logger.warn("The user has just been greeted!");
		logger.error("The user has just been greeted!");
		logger.fatal("The user has just been greeted!");
	}

}
