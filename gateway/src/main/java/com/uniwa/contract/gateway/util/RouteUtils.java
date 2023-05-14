package com.uniwa.contract.gateway.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteUtils {

    @Value("#{'${spring.cloud.gateway.excludedUrls}'.split(',')}")
    private List<String> excludedUrls = new ArrayList<>();

    public Predicate<ServerHttpRequest> isSecured =
            request -> excludedUrls
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
