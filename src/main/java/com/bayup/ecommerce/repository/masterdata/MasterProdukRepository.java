package com.bayup.ecommerce.repository.masterdata;

import com.bayup.ecommerce.model.masterdata.MasterProduk;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterProdukRepository extends JpaRepository<MasterProduk, String>{
    
}