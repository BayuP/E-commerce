package com.bayup.ecommerce.dto.transaksi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransaksiDto {
    
    private String produkId;

    private double totalPrice;

    private int totalAmount;

}