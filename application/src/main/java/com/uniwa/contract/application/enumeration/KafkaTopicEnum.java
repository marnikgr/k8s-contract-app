package com.uniwa.contract.application.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KafkaTopicEnum {

    SUBMITTED("application.submitted.topic"),
    PROCESSED("application.processed.topic"),
    COMPLETED("application.completed.topic"),
    CANCELED("application.canceled.topic");

    private final String topic;


}
