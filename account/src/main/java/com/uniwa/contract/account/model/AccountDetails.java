package com.uniwa.contract.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 7546841786392490555L;

    private String userId;

    private String fullName;

    private String lastName;

    private String givenName;

    private String displayName;

    private String email;

}
