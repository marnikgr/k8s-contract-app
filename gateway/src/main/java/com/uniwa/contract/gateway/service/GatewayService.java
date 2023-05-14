package com.uniwa.contract.gateway.service;

import com.uniwa.contract.gateway.client.AccountClient;
import com.uniwa.contract.gateway.model.AuthRequest;
import com.uniwa.contract.gateway.model.AuthResponse;
import com.uniwa.contract.gateway.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class GatewayService {

    private final AccountClient accountClient;

    private final JwtUtils jwtUtils;

    public AuthResponse authenticate(AuthRequest request) {

        String username = accountClient.authenticate(request);
        String jwt = jwtUtils.generate(username, new HashMap<>());
        return AuthResponse.builder()
                .username(username)
                .jwt(jwt)
                .build();
    }
}
