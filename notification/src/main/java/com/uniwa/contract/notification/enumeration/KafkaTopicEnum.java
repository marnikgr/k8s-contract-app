package com.uniwa.contract.notification.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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

    @JsonCreator
    public static KafkaTopicEnum fromValue(String value) {
        for (KafkaTopicEnum s : values()) {
            if (String.valueOf(s.topic).equals(value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(topic);
    }


}
