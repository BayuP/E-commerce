package com.bayup.ecommerce.model.masterdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MASTER_PRODUCT")
public class MasterProduk {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id")
    private String id;
    
    @Column(name = "code")
    private String code;

    @Column(name = "nama")
    private String nama;

    @Column(name = "harga")
    private double harga;

    @Column(name = "stok")
    private int stok;

    @ManyToOne
    @JoinColumn(name = "kategori_id")
    private MasterKategori masterKategori;

    
}