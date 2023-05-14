package com.uniwa.contract.notification.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class EmailInfo {

    private String email;

    private String subject;

    private Map<String, Object> values;
}
