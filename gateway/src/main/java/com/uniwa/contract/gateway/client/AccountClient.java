package com.uniwa.contract.gateway.client;

import com.uniwa.contract.gateway.config.FeignClientConfig;
import com.uniwa.contract.gateway.model.AuthRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account", url = "${account-client.url}", configuration = FeignClientConfig.class)
public interface AccountClient {

    @PostMapping(value = "account/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    String authenticate(@RequestBody AuthRequest request);
}
