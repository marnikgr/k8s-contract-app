package com.uniwa.contract.application;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ApplicationTests {

	@ClassRule
	public static EmbeddedKafkaRule broker = new EmbeddedKafkaRule(1,
			false, "application.submitted.topic", "application.processed.topic",
			"application.completed.topic", "application.canceled.topic");

	@BeforeClass
	public static void setup() {
		System.setProperty("spring.kafka.bootstrap-servers",
				broker.getEmbeddedKafka().getBrokersAsString());
	}

	@Test
	void contextLoads() {
	}

}
