package com.uniwa.contract.account.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LdapAttributesEnum {

    UID("uid"),
    CN("cn"),
    SN("sn"),
    GIVEN_NAME("givenname"),
    DISPLAY_NAME("displayname"),
    MAIL("mail"),
    ORG_USERS("ou=Users");

    private final String value;

    @JsonCreator
    public static LdapAttributesEnum fromValue(String value) {
        for (LdapAttributesEnum a : values()) {
            if (String.valueOf(a.value).equals(value)) {
                return a;
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
