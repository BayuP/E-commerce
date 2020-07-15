package com.bayup.ecommerce.controller.transaksi;

import java.util.List;
import java.util.Optional;

import com.bayup.ecommerce.dto.transaksi.TransaksiDto;
import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.Response.ResponseStatus;
import com.bayup.ecommerce.model.masterdata.MasterProduk;
import com.bayup.ecommerce.model.transaksi.Transaksi;
import com.bayup.ecommerce.model.transaksi.Transaksi.StatusTransaksi;
import com.bayup.ecommerce.repository.masterdata.MasterProdukRepository;
import com.bayup.ecommerce.repository.transaksi.TransaksiRepository;
import com.bayup.ecommerce.service.AuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaksi")
public class TransaksiController {
    
    @Autowired
    TransaksiRepository transaksiRepo;

    @Autowired
    MasterProdukRepository produkRepo; 

    @PostMapping("/")
    public Response createTransaksi(@RequestBody TransaksiDto dto){
        Response result = new Response();
        AuthUserService userAuth = (AuthUserService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Transaksi entity = new Transaksi();

        Optional<MasterProduk> pOptional = produkRepo.findById(dto.getProdukId());
        
        if(!pOptional.isPresent()){
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            MasterProduk masterProduk =  pOptional.get();
            
            if(dto.getTotalAmount() > masterProduk.getStok()){
                return result.Result(ResponseStatus.OK, null);
            }
            
            masterProduk.setStok(masterProduk.getStok() - dto.getTotalAmount());
            produkRepo.save(masterProduk);
        }
        
        entity.setProdukId(dto.getProdukId());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setUserId(userAuth.getID());
        entity.setStatus(StatusTransaksi.SUCCESS);

        transaksiRepo.save(entity);

        return result.Result(ResponseStatus.CREATED, entity);
    }


    @GetMapping("/")
    public Response AllTransaksiByUserID(){
        Response result = new Response();

        AuthUserService userAuth = (AuthUserService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        List<Transaksi> allTransaksi = transaksiRepo.findByUserId(userAuth.getID());
        
        // for (Transaksi transaksi : allTransaksi) {
        //     transaksi.getStatus().toString();
        // }

        return result.Result(ResponseStatus.OK, allTransaksi);

    }
}