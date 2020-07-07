package com.bayup.ecommerce.controller.masterdata;

import java.util.Optional;

import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.masterdata.MasterProduk;
import com.bayup.ecommerce.repository.masterdata.MasterProdukRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    //Get all Produk
    @GetMapping("/all")
    public Response allProduct(){
        
        Response result = new Response();
        result.setStatusCode(HttpStatus.OK.value());
        result.setMessage("Get All Product");
        result.setData(masterProdukRepo.findAll());

        return result;
    }

    //Get one produk
    @GetMapping("/")
    public Response product(@RequestParam("id") String id){

        Response result = new Response();
        if(masterProdukRepo.existsById(id)){
            result.setData(masterProdukRepo.findById(id));
            result.setMessage("Get User Successfull");
            result.setStatusCode(HttpStatus.OK.value());

        }else{
            result.setData(null);
            result.setMessage("User not found");
            result.setStatusCode(HttpStatus.NOT_FOUND.value());
        }

        return result;

    }

    //Create new Produk
    @PostMapping("/")
    public Response createProduk(@RequestBody MasterProduk masterProduk){

        Response result = new Response();
        masterProdukRepo.save(masterProduk);
        
        result.setStatusCode(HttpStatus.CREATED.value());
        result.setMessage("Create new produk");
        result.setData(masterProduk);

        return result;
    }

    //delete produk
    @DeleteMapping("/{id}")
    public Response deleteProduk(@PathVariable("id") String id){

        Response result = new Response();
        masterProdukRepo.deleteById(id);

        result.setStatusCode(HttpStatus.OK.value());
        result.setMessage("Succes delete produk");
        result.setData(null);

        return result;
    }

    //Edit Produk
    @PutMapping("/{id}")
    public Response editProduk(@RequestBody MasterProduk masterProduk, 
    @PathVariable String id){

        Response result = new Response();
        Optional<MasterProduk> produkOptional = masterProdukRepo.findById(id);
        
        if(!produkOptional.isPresent()){
            result.setStatusCode(HttpStatus.NOT_FOUND.value());
            result.setData(null);
            result.setMessage("Data not found");
        }else{
            MasterProduk newProduk =  produkOptional.get();
            newProduk.setNama(masterProduk.getNama());
            newProduk.setCode(masterProduk.getCode());
            newProduk.setHarga(masterProduk.getHarga());
            newProduk.setStok(masterProduk.getStok());

            masterProdukRepo.save(newProduk);
            result.setStatusCode(HttpStatus.OK.value());
            result.setData(newProduk);
            result.setMessage("Succes edit data");
        }
        return result;
    }



}