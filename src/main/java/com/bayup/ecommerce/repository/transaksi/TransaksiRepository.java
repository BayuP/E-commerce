package com.bayup.ecommerce.repository.transaksi;

import java.util.List;

import com.bayup.ecommerce.model.transaksi.Transaksi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransaksiRepository extends JpaRepository<Transaksi, String>{
    
    @Query(value = "select * from transaksi where user_id = ?1", nativeQuery = true)
    public List<Transaksi> findByUserId(String userId);
}