package com.uniwa.contract.gateway.controller;

import com.uniwa.contract.gateway.model.AuthRequest;
import com.uniwa.contract.gateway.model.AuthResponse;
import com.uniwa.contract.gateway.service.GatewayService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class GatewayController {

    private final GatewayService gatewayService;

    @PostMapping(value = "/account/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {

        return ResponseEntity.ok(gatewayService.authenticate(request));
    }


}
