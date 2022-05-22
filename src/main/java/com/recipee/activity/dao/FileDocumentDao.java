package com.recipee.activity.dao;

import com.recipee.activity.model.FileDocumentImpl;
import com.recipee.activity.model.RecipeDocumentImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDocumentDao extends MongoRepository<FileDocumentImpl, String> {
}
