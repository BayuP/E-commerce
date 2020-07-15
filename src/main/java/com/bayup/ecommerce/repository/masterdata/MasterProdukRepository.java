package com.bayup.ecommerce.repository.masterdata;

import java.util.List;

import com.bayup.ecommerce.model.masterdata.MasterProduk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MasterProdukRepository extends JpaRepository<MasterProduk, String> {

    @Query(value = "select * from master_product where kategori_id = ?1", nativeQuery = true)
    public List<MasterProduk> findKategoriById(String kategori);

   
}