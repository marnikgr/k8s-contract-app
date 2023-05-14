package com.uniwa.contract.account.service;

import com.uniwa.contract.account.enumeration.LdapAttributesEnum;
import com.uniwa.contract.account.exception.InvalidAccountCredentials;
import com.uniwa.contract.account.model.AccountDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AccountService {

    private final LdapTemplate ldapTemplate;

    private final RedisCacheService redisCacheService;

    public String authenticate(String username, String password) throws InvalidAccountCredentials {

        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter(LdapAttributesEnum.MAIL.getValue(), username));
        boolean result = ldapTemplate.authenticate(LdapAttributesEnum.ORG_USERS.getValue(),
                filter.toString(), password);
        if (!result) {
            throw new InvalidAccountCredentials("Invalid username or password for username: " + username);
        }
        AccountDetails accountDetails = retrieveAccountDetailsFromLdap(username);
        redisCacheService.save(accountDetails);

        return accountDetails.getEmail();
    }

    public AccountDetails getAccountDetails(String username) {

       return redisCacheService.fetch(username);
    }

    private AccountDetails retrieveAccountDetailsFromLdap(String username) throws InvalidAccountCredentials {

        return ldapTemplate.search(
                        LdapAttributesEnum.ORG_USERS.getValue(),
                        LdapAttributesEnum.MAIL.getValue() + "=" + username,
                        (AttributesMapper<AccountDetails>) attributes -> AccountDetails.builder()
                                .userId((String) attributes.get(LdapAttributesEnum.UID.getValue()).get())
                                .fullName((String) attributes.get(LdapAttributesEnum.CN.getValue()).get())
                                .lastName((String) attributes.get(LdapAttributesEnum.SN.getValue()).get())
                                .givenName((String) attributes.get(LdapAttributesEnum.GIVEN_NAME.getValue()).get())
                                .displayName((String) attributes.get(LdapAttributesEnum.DISPLAY_NAME.getValue()).get())
                                .email((String) attributes.get(LdapAttributesEnum.MAIL.getValue()).get())
                                .build()
                ).stream().findAny()
                .orElseThrow(() -> new InvalidAccountCredentials("Account not found for username " + username));
    }


}
