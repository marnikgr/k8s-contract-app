package com.uniwa.contract.application.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ApplicationStatusEnum {

    SUBMITTED("SUBMITTED"),
    PROCESSED("PROCESSED"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String value;

    @JsonCreator
    public static ApplicationStatusEnum fromValue(String value) {
        for (ApplicationStatusEnum s : values()) {
            if (String.valueOf(s.value).equals(value)) {
                return s;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
