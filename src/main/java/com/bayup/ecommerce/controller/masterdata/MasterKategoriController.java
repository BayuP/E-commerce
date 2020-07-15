package com.bayup.ecommerce.controller.masterdata;

import java.util.List;
import java.util.Optional;

import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.Response.ResponseStatus;
import com.bayup.ecommerce.model.masterdata.MasterKategori;
import com.bayup.ecommerce.model.masterdata.MasterProduk;
import com.bayup.ecommerce.repository.masterdata.MasterKategoriRepositori;
import com.bayup.ecommerce.repository.masterdata.MasterProdukRepository;
import com.bayup.ecommerce.service.AuthUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/kategori")
public class MasterKategoriController {
    
    @Autowired
    MasterKategoriRepositori masterKategoriRepo;

    @Autowired
    MasterProdukRepository masterProdukrepo;

    //Get all Kategori
    @GetMapping("/all")
    public Response getAllKategori(){
        //AuthUserService principal = (AuthUserService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        
        Response result = new Response();
        //result.Result(ResponseStatus.OK, principal.getID());
        result.Result(ResponseStatus.OK, masterKategoriRepo.findAll());

        return result;
    }

    //get on kategori
    @GetMapping("/")
    public Response getOneKategori(@RequestParam("id") String id){
        Response result = new Response();

        if(masterKategoriRepo.existsById(id)){
            result.Result(ResponseStatus.OK, masterKategoriRepo.findById(id));
        }else{
            result.Result(ResponseStatus.NOT_FOUND, null);
        }

        return result;
    }

    //Post Kategori
    @PostMapping("/")
    public Response CreateKategori(@RequestBody MasterKategori masterKategori){
        Response result = new Response();

        masterKategoriRepo.save(masterKategori);

        result.Result(ResponseStatus.CREATED, masterKategori);

        return result;
    }

    //delete kategori
    @DeleteMapping("/{id}")
    public Response deleteProduk(@PathVariable("id") String id){

        Response result = new Response();

        if(!masterKategoriRepo.existsById(id)){
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            List<MasterProduk> msProduk = masterProdukrepo.findKategoriById(id);
            if(msProduk.size() > 0){
                result.Result(ResponseStatus.FAILED_DEPENCENCY, null);
            }else{
                masterKategoriRepo.deleteById(id);

                result.Result(ResponseStatus.OK, null);
            }
            
        }
        

        return result;
    }

    //Edit kategori
    @PutMapping("/{id}")
    public Response editProduk(@RequestBody MasterKategori masterKategori, 
    @PathVariable String id){

        Response result = new Response();
        Optional<MasterKategori> kategoriOptional = masterKategoriRepo.findById(id);
        
        if(!kategoriOptional.isPresent()){
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            MasterKategori newKategori =  kategoriOptional.get();
            newKategori.setNama(masterKategori.getNama());

            masterKategoriRepo.save(newKategori);
            result.Result(ResponseStatus.OK, newKategori);
        }
        return result;
    }



}