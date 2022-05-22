package com.recipee.activity.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recipee.activity.dto.LikeCommentRequest;
import com.recipee.activity.dto.RecipeDocumentDTO;
import com.recipee.activity.model.LikeCommentDocImpl;
import com.recipee.activity.model.RecipeDocumentImpl;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface RecipeActivityService {

    RecipeDocumentDTO createRecipe(String recipeDocument, List<MultipartFile> multipartFiles, String username) throws JsonProcessingException, FileNotFoundException, FileUploadException;

    List<RecipeDocumentImpl> getAllRecipes();

    List<RecipeDocumentImpl> getRecipe(String search);

    LikeCommentDocImpl postLikeAndComment(LikeCommentRequest search);
}
