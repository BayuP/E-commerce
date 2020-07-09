package com.bayup.ecommerce.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bayup.ecommerce.model.masterdata.MasterUser;
import com.bayup.ecommerce.repository.masterdata.MasterUserRepository;

@Service
public class JwtUserDetailService implements UserDetailsService{
    
    @Autowired
    MasterUserRepository masterUserRepo;
    

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MasterUser user = masterUserRepo.findByUsername(username);
        if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
}