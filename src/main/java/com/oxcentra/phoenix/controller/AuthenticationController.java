package com.oxcentra.phoenix.controller;

import com.oxcentra.phoenix.common.JwtUtility;
import com.oxcentra.phoenix.dto.JwtResponse;
import com.oxcentra.phoenix.dto.JwtRequest;
import com.oxcentra.phoenix.service.AuthenticationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private  AuthenticationService authenticationService;

//    @Qualifier("authenticationManagerBean")
    @Autowired
    private  AuthenticationManager authenticationManager;

    public AuthenticationController(JwtUtility jwtUtility,AuthenticationService authenticationService,AuthenticationManager authenticationManager){
       this.jwtUtility = jwtUtility;
        this.authenticationService=authenticationService;
        this.authenticationManager=authenticationManager;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity authentication(@RequestBody JwtRequest jwtRequest) throws Exception {

        log.info(jwtRequest.getEmail());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));
        }catch (BadCredentialsException e){
            log.info("Error");
            throw new Exception("Invalid credential",e);

        }


        final String resultCode="SUCCESS";
        final String token=jwtUtility.generateToken(jwtRequest.getEmail());
        final Date expiresAt=jwtUtility.extractExpiration(token);
        final int expiresIn=jwtUtility.jwtExpirationInMs;

        log.info(token);


        return new ResponseEntity(
                new JwtResponse(resultCode, token, expiresAt, expiresIn),HttpStatus.OK);
    }


}