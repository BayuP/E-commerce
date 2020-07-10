package com.bayup.ecommerce.controller.masterdata;

import com.bayup.ecommerce.dto.masterdata.MasterRoleDto;
import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.Response.ResponseStatus;
import com.bayup.ecommerce.model.masterdata.MasterRole;
import com.bayup.ecommerce.repository.masterdata.MasterRoleRepositori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class MasterRoleController {
    

    @Autowired
    MasterRoleRepositori masterRoleRepo;

    @PostMapping("/")
    public Response createRole(@RequestBody MasterRoleDto dto){
        Response result = new Response();

        MasterRole existRole = masterRoleRepo.findRoleByName(dto.getNama().toUpperCase());
        
        MasterRole entity = new MasterRole();
        
        if (existRole == null){
            entity.setNama(dto.getNama().toUpperCase());
            masterRoleRepo.save(entity);
            return result.Result(ResponseStatus.OK, entity);
        }else{
            return result.Result(ResponseStatus.CONFLICT, null);
        }
        
    }
}