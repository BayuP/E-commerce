package com.bayup.ecommerce.controller.auth;

import java.security.NoSuchAlgorithmException;

import com.bayup.ecommerce.dto.auth.AuthUserDto;
import com.bayup.ecommerce.model.Response;
import com.bayup.ecommerce.model.Response.ResponseStatus;
import com.bayup.ecommerce.model.masterdata.MasterUser;
import com.bayup.ecommerce.repository.masterdata.MasterUserRepository;
import com.bayup.ecommerce.util.MD5Hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bayup.ecommerce.config.JwtTokenUtil;
import com.bayup.ecommerce.service.JwtUserDetailService;
import com.bayup.ecommerce.model.auth.JwtRequest;
import com.bayup.ecommerce.model.auth.JwtResponse;

import org.springframework.security.core.userdetails.UserDetails;


@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthUserController {
    
    @Autowired
    MasterUserRepository masterUserRepo;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailService userDetailsService;

    @PostMapping("/login")
    public Response loginUser(@RequestBody AuthUserDto authDto){
        
        Response result = new Response();

        MasterUser findUser = masterUserRepo.findByUsername(authDto.getUsername());
        
        if(findUser == null){
            result.Result(ResponseStatus.FORBIDDEN, null);
        }else{

            try{
                final String hashPassword = MD5Hash.MD5Encrypt(authDto.getPassword()+findUser.getSalt());
                if(findUser.getPassword().equals(hashPassword)){
                    result.Result(ResponseStatus.OK, findUser);
                }else{
                    result.Result(ResponseStatus.FORBIDDEN, hashPassword+findUser.getPassword());
                }
            }catch(NoSuchAlgorithmException ex){
                throw new RuntimeException("Encrypt user password error", ex);
            }

        }

        return result;
    }


    @PostMapping("/authenticate")
    public Response createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        
        Response result = new Response();

        MasterUser findUser = masterUserRepo.findByUsername(authenticationRequest.getUsername());

        if (findUser == null){
            result.Result(ResponseStatus.NOT_FOUND, null);
        }else{
            try{
                final String hashPassword = MD5Hash.MD5Encrypt(authenticationRequest.getPassword()+findUser.getSalt());
                authenticationRequest.setPassword(hashPassword);
            }catch(NoSuchAlgorithmException ex){
                throw new RuntimeException("Encrypt user password error", ex);
            }
    
            if(!authenticationRequest.getPassword().equals(findUser.getUsername()) && !authenticationRequest.getPassword().equals(findUser.getPassword())){
                result.Result(ResponseStatus.BAD_REQUEST, null);
            }else{
                // authenticate(authenticationRequest.getUsername(),
                // authenticationRequest.getPassword());
                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(authenticationRequest.getUsername());
                final String token = jwtTokenUtil.generateToken(userDetails);
                result.Result(ResponseStatus.OK, new JwtResponse(token));
                
            }
    
         
        }

        return result;
       
    }

    // private void authenticate(String username, String password) throws Exception {

    //     MasterUser findUser = masterUserRepo.findByUsername(username);

        

    //     // try {
    //     //     // authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    //     //     if()
    //     // } catch (DisabledException e) {
    //     //     throw new Exception("USER_DISABLED", e);
    //     // } catch (BadCredentialsException e) {
    //     //     throw new Exception(findUser.getPassword()+" "+password, e);
    //     // }
    // }

}