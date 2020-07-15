package com.bayup.ecommerce.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateUser {
    
    private boolean enabled;
    
    private Object auth;

    private String username;

    private String password;

    private String id;

    private boolean expiredAccount;

    private boolean lockedAccount;

    private boolean expiredCred;


}