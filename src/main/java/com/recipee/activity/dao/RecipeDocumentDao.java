package com.recipee.activity.dao;

import com.recipee.activity.model.RecipeDocumentImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeDocumentDao extends MongoRepository<RecipeDocumentImpl, String> {
    Optional<RecipeDocumentImpl> findById(String id);
    List<RecipeDocumentImpl> findByIngredientsName(String name);

}
