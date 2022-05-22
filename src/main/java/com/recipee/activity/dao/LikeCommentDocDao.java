package com.recipee.activity.dao;

import com.recipee.activity.model.LikeCommentDocImpl;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LikeCommentDocDao extends MongoRepository<LikeCommentDocImpl, String> {
    Optional<LikeCommentDocImpl> findByUserIdAndRecipeId(String userId, String recipeId);
    Optional<LikeCommentDocImpl> findByUsernameAndRecipeId(String username, String recipeId);
}
