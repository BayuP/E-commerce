package com.bayup.ecommerce.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.bayup.ecommerce.model.masterdata.MasterRole;
import com.bayup.ecommerce.model.masterdata.MasterUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserService implements UserDetails{

    private MasterUser masterUser;

    public AuthUserService(MasterUser masterUser) {
        this.masterUser = masterUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<MasterRole> roles = masterUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (MasterRole role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNama()));
        }
         
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return masterUser.getPassword();
    }
 
    @Override
    public String getUsername() {
        return masterUser.getUsername();
    }

    public String getID(){
        return masterUser.getId();
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
 
 
}