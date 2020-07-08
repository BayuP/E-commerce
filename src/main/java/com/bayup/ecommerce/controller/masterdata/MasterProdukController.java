package com.bayup.ecommerce.controller.masterdata;

import java.util.Optional;

import com.bayup.ecommerce.dto.masterdata.MasterProdukDto;
import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.Response.ResponseStatus;
import com.bayup.ecommerce.model.masterdata.MasterKategori;
import com.bayup.ecommerce.model.masterdata.MasterProduk;
import com.bayup.ecommerce.repository.masterdata.MasterKaterogiRepositori;
import com.bayup.ecommerce.repository.masterdata.MasterProdukRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produk")
public class MasterProdukController {
    
    @Autowired
    MasterProdukRepository masterProdukRepo;

    @Autowired
    MasterKaterogiRepositori masterKategoriRepo;

    //Get all Produk
    @GetMapping("/all")
    public Response allProduct(){
        
        Response result = new Response();
        result.Result(ResponseStatus.OK, masterProdukRepo.findAll());

        return result;
    }

    //Get one produk
    @GetMapping("/")
    public Response product(@RequestParam("id") String id){

        Response result = new Response();
        if(masterProdukRepo.existsById(id)){
            result.Result(ResponseStatus.OK, masterProdukRepo.findById(id));

        }else{
            result.Result(ResponseStatus.OK, null);
        }
        return result;

    }

    //Create new Produk
    @PostMapping("/")
    public Response createProduk(@RequestBody MasterProdukDto masterProdukDto){

        MasterProduk entity = new MasterProduk();
        Response result = new Response();
        Optional<MasterKategori> kOptional = masterKategoriRepo.findById(masterProdukDto.getMasterKategoriDto().getId());
        
        if(!kOptional.isPresent()) {
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            
            entity.setMasterKategori(kOptional.get());
            entity.setCode(masterProdukDto.getCode());
            entity.setNama(masterProdukDto.getNama());
            entity.setHarga(masterProdukDto.getHarga());
            entity.setStok(masterProdukDto.getStok());

            masterProdukRepo.save(entity);

            result.Result(ResponseStatus.CREATED, entity);
        }
        return result;
    }

    //delete produk
    @DeleteMapping("/{id}")
    public Response deleteProduk(@PathVariable("id") String id){

        Response result = new Response();

        if(!masterProdukRepo.existsById(id)){
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            masterProdukRepo.deleteById(id);

            result.Result(ResponseStatus.OK, null);
        }
        

        return result;
    }

    //Edit Produk
    @PutMapping("/{id}")
    public Response editProduk(@RequestBody MasterProdukDto masterProdukDto, 
    @PathVariable String id){

        Response result = new Response();
        Optional<MasterProduk> produkOptional = masterProdukRepo.findById(id);
        
        if(!produkOptional.isPresent()){
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            Optional<MasterKategori> kOptional = masterKategoriRepo.findById(masterProdukDto.getMasterKategoriDto().getId());
            if(!kOptional.isPresent()){
                result.Result(ResponseStatus.BAD_REQUEST, null);
            }else{
                MasterProduk newProduk =  produkOptional.get();

                newProduk.setNama(masterProdukDto.getNama());
                newProduk.setCode(masterProdukDto.getCode());
                newProduk.setHarga(masterProdukDto.getHarga());
                newProduk.setStok(masterProdukDto.getStok());
                newProduk.setMasterKategori(kOptional.get());

                masterProdukRepo.save(newProduk);
                result.Result(ResponseStatus.OK, newProduk);
            }
            
        }
        return result;
    }



}