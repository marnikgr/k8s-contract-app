package com.uniwa.contract.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessage {

    @Serial
    private static final long serialVersionUID = 4365743648025150554L;

    private Long applicationId;

    private String applicantEmail;

    private String applicantFullName;

}
