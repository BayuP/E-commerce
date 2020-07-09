package com.bayup.ecommerce.repository.masterdata;

import com.bayup.ecommerce.model.masterdata.MasterUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterUserRepository extends JpaRepository<MasterUser,String>{

    @Query(value="select * from master_user where username = ?1", nativeQuery = true)
    public MasterUser findByUsername(String username);

    @Query(value="select * from master_user where username = ?1 and password = ?2", nativeQuery = true)
    public MasterUser findByUsernameAndPassword(String username, String password);

}