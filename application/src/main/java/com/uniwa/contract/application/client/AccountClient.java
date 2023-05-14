package com.uniwa.contract.application.client;

import com.uniwa.contract.application.model.AccountDetails;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account", url = "${account-client.url}")
public interface AccountClient {

    @GetMapping(value = "/account/v1/get/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccountDetails> accountDetails(@PathVariable String username)
            throws FeignException.FeignClientException;
}
