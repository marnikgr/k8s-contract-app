package com.uniwa.contract.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniwa.contract.account.controller.AccountController;
import com.uniwa.contract.account.model.AccountDetails;
import com.uniwa.contract.account.model.AuthRequest;
import com.uniwa.contract.account.service.RedisCacheService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AccountApplicationTests {

	MockMvc mockMvc;

	@Autowired
	AccountController accountController;

	@MockBean
	LdapTemplate ldapTemplate;

	@MockBean
	RedisCacheService redisCacheService;

	ObjectMapper om = new ObjectMapper();

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
	}

	@Test
	@SneakyThrows
	void authenticateTest() {

		Mockito.when(ldapTemplate.authenticate(Mockito.any(String.class), Mockito.any(String.class),
						Mockito.any(String.class)))
				.thenReturn(true);

		Mockito.when(ldapTemplate.search(Mockito.any(String.class), Mockito.any(String.class),
						Mockito.any(AttributesMapper.class)))
				.thenReturn(List.of(AccountDetails.builder().email("test").build()));

		Mockito.doNothing().when(redisCacheService).save(Mockito.any(AccountDetails.class));

		mockMvc.perform(post("/account/v1/auth")
						.content(om.writeValueAsString(new AuthRequest("test", "test")))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
