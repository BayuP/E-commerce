package com.bayup.ecommerce.dto.masterdata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterProdukDto {
    
    private String id;

    private String code;

    private String nama;

    private Double harga;

    private int stok;

}