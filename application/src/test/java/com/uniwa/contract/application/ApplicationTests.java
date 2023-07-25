package com.uniwa.contract.application;

import com.uniwa.contract.application.client.AccountClient;
import com.uniwa.contract.application.controller.ApplicationController;
import com.uniwa.contract.application.model.AccountDetails;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EmbeddedKafka(topics = {"application.submitted.topic","application.processed.topic","application.completed.topic","application.canceled.topic"},
		bootstrapServersProperty = "spring.kafka.bootstrap-servers")
@DirtiesContext
@ExtendWith(SpringExtension.class)
class ApplicationTests {

	MockMvc mockMvc;

	@Autowired
	ApplicationController applicationController;

	@MockBean
	AccountClient accountClient;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(applicationController).build();
	}

	@Test
	@SneakyThrows
	void createTest() {

		ResponseEntity<AccountDetails> accountDetails = new ResponseEntity<>(AccountDetails.builder()
				.userId("testId").email("test@test.gr").fullName("test test").build(), HttpStatus.OK);
		Mockito.when(accountClient.accountDetails(Mockito.any(String.class)))
				.thenReturn(accountDetails);

		mockMvc.perform(post("/application/v1/test")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

}
