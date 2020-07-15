package com.bayup.ecommerce.model.transaksi;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="transaksi")
public class Transaksi {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "produk_id")
    private String produkId;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "status")
    private StatusTransaksi status;


    public enum StatusTransaksi{
        SUCCESS,
        PENDING,
        FAILED
    }
}

