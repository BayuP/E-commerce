package com.bayup.ecommerce.repository.masterdata;

import com.bayup.ecommerce.model.masterdata.MasterRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterRoleRepositori extends JpaRepository<MasterRole, String> {
    
    @Query(value="select * from master_role where nama = ?1", nativeQuery = true)
    public MasterRole findRoleByName(String name);
}