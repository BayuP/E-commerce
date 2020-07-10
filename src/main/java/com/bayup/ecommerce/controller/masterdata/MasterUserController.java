package com.bayup.ecommerce.controller.masterdata;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import com.bayup.ecommerce.dto.masterdata.MasterUserDto;
import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.Response.ResponseStatus;
import com.bayup.ecommerce.model.masterdata.MasterRole;
import com.bayup.ecommerce.model.masterdata.MasterUser;
import com.bayup.ecommerce.repository.masterdata.MasterRoleRepositori;
import com.bayup.ecommerce.repository.masterdata.MasterUserRepository;
import com.bayup.ecommerce.util.MD5Hash;
import com.bayup.ecommerce.util.UniqueID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class MasterUserController {
    

    @Autowired
    MasterUserRepository masterUserRepo;

    @Autowired
    MasterRoleRepositori masterRoleRepo;


    @PostMapping("/")
    public Response createUser(@RequestBody MasterUserDto userDto){
        
        MasterUser entity = new MasterUser();
        Response result = new Response();
        MasterUser existUser = masterUserRepo.findByUsername(userDto.getUsername());
        MasterRole role = masterRoleRepo.findRoleByName("USER");

        Set<MasterRole> roles = new HashSet<>();
        roles.add(role);
        if(existUser == null){
            entity.setUsername(userDto.getUsername());
            entity.setSalt(UniqueID.getUUID());
            entity.setNama(userDto.getNama());
            entity.setRoles(roles);

            try{
                entity.setPassword(MD5Hash.MD5Encrypt(userDto.getPassword() + entity.getSalt()));
            }catch(NoSuchAlgorithmException ex){
                throw new RuntimeException("Encrypt user password error", ex);
            }

            masterUserRepo.save(entity);
            
            result.Result(ResponseStatus.CREATED, entity);

        }else{
            result.Result(ResponseStatus.CONFLICT, null);
        }

        return result;
    }

    

}