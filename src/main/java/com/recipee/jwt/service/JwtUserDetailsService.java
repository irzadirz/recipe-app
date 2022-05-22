package com.recipee.jwt.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

import com.recipee.activity.model.FileDocumentImpl;
import com.recipee.activity.model.IntentType;
import com.recipee.activity.services.FileProperties;
import com.recipee.activity.services.FileService;
import com.recipee.jwt.dao.AuthorisationDao;
import com.recipee.jwt.model.AuthorisationDocumentDto;
import com.recipee.jwt.model.AuthorisationDocumentImpl;
import com.recipee.jwt.model.ProfileImageDetails;
import com.recipee.utils.FileCommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private FileService fileService;
    @Autowired
    private FileProperties fileProperties;
    @Autowired
    private AuthorisationDao userDao;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthorisationDocumentImpl user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }


    public boolean userExist(String username) throws UsernameNotFoundException {
        AuthorisationDocumentImpl user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }

    public AuthorisationDocumentImpl save(String userAuthDetails, MultipartFile multipartFile) throws JsonProcessingException, FileUploadException, FileNotFoundException {
        ObjectMapper mapper=new ObjectMapper();
        AuthorisationDocumentDto user = mapper.readValue(userAuthDetails, AuthorisationDocumentDto.class);

        if(!Objects.isNull(userDao.findByUsername(user.getUsername())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");

        AuthorisationDocumentImpl newUser = new AuthorisationDocumentImpl();
        newUser.setName(user.getName());
        newUser.setUsername(user.getUsername());
        newUser.setProfileImageDoc(uploadProfileImage(multipartFile, user.getUsername()));
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }

    private FileDocumentImpl uploadProfileImage(MultipartFile multipartFile, String name) throws FileUploadException, FileNotFoundException {
        if (multipartFile!=null && !multipartFile.isEmpty()) {
            var f = FileCommonUtils.upload(
                    multipartFile, fileProperties.getUploadPath(), name, fileProperties.getProfileImageFolder(), IntentType.PROFILE_PICTURE, name);
            fileService.createFile(f);
            return f;
        }
        return null;
    }

}