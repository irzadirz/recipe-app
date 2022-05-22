package com.recipee.jwt.dao;

import com.recipee.jwt.model.AuthorisationDocumentImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorisationDao extends MongoRepository<AuthorisationDocumentImpl, String> {
    AuthorisationDocumentImpl findByUsername(String username);
}
