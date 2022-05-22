package com.recipee.jwt.controllers;

import com.recipee.jwt.controllers.dto.JwtRequest;
import com.recipee.jwt.controllers.dto.JwtResponse;
import com.recipee.jwt.controllers.dto.Response;
import com.recipee.jwt.service.JwtUserDetailsService;
import com.recipee.jwt.config.JwtTokenUtil;
import com.recipee.utils.EndPointConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@Slf4j
public class LoginAndSignController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = EndPointConstants.SLASH_AUTHENTICATE, method = RequestMethod.POST)
    public Response<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return new Response("success", new JwtResponse(token));
    }


    @RequestMapping(value = EndPointConstants.SLASH_REGISTER, method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestPart("user-data") String userAuthDetails,
                                      @RequestParam("profile-picture") MultipartFile multipartFile) throws Exception {

        return ResponseEntity.ok(userDetailsService.save(userAuthDetails,multipartFile));
    }

}
