package com.recipee.activity.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipee.activity.dao.LikeCommentDocDao;
import com.recipee.activity.dao.RecipeDocumentDao;
import com.recipee.activity.dto.LikeCommentRequest;
import com.recipee.activity.dto.RecipeDocumentDTO;
import com.recipee.activity.model.*;
import com.recipee.utils.CollectionConstants;
import com.recipee.utils.FileCommonUtils;
import com.recipee.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
@Slf4j
public class RecipeActivityServiceImpl implements RecipeActivityService {

    private RecipeDocumentDao recipeDocumentDao;
    private LikeCommentDocDao likeCommentDocDao;
    private FileProperties fileProperties;
    private FileService fileService;

    @Override
    public RecipeDocumentDTO createRecipe(String recipeDocumentString, List<MultipartFile> multipartFiles, String username) throws JsonProcessingException, FileNotFoundException, FileUploadException {
        ObjectMapper mapper=new ObjectMapper();
        RecipeDocumentImpl recipeDocument = mapper.readValue(recipeDocumentString, RecipeDocumentImpl.class);

        if (Utils.isNull(recipeDocument))
            throw new NullPointerException("recipe-data is empty");
        else if (Utils.isNullOrEmptyCollection(recipeDocument.getIngredients()))
            throw new NullPointerException("ingredients is missing");
        else if (Utils.isNullOrEmptyCollection(recipeDocument.getSteps()))
            throw new NullPointerException("Steps is missing");
        List<FileDocumentImpl> fileDocuments = null;
        String uploadPath = fileProperties.getUploadPath();
        if (!Utils.isNullOrEmptyCollection(multipartFiles)) {
            fileDocuments = FileCommonUtils.uploadMultipleFiles(multipartFiles, uploadPath, recipeDocument.getName(),
                    fileProperties.getAttachmentsFolder(), IntentType.RECIPE_IMAGE, username);
            fileService.createMultipleFiles(fileDocuments);
        }
        recipeDocument.setOwner(username);
        recipeDocument.setAttachedImages(fileDocuments);
        RecipeDocumentImpl result = recipeDocumentDao.save(recipeDocument);
        log.info(CollectionConstants.RECIPES + " created successfully");
        String message = "success";
        RecipeDocumentDTO recipeDocumentDTO = result.toRecipeDocumentDTO();
        return recipeDocumentDTO;
    }

    @Override
    public List<RecipeDocumentImpl> getAllRecipes() {
        return recipeDocumentDao.findAll();
    }

    @Override
    public List<RecipeDocumentImpl> getRecipe(String search) {
        return recipeDocumentDao.findByIngredientsName(search);
    }


    @Override
    public LikeCommentDocImpl postLikeAndComment(LikeCommentRequest request) {
        if (Utils.isNullOrEmpty(request.getRecipeId()))
            throw new NullPointerException("recipeId is mandatory");
        if (Utils.isNullOrEmpty(request.getUserId()))
            throw new NullPointerException("UserId is mandatory");
        if (Utils.isNullOrEmpty(request.getUsername()))
            throw new NullPointerException("Username is mandatory");

        var find = likeCommentDocDao.findByUsernameAndRecipeId(request.getUsername(), request.getRecipeId());
        LikeCommentDocImpl likeCommentDoc = null;
        if (find.isPresent()) {
            likeCommentDoc = find.get();
            likeCommentDoc.setLike(request.isLike());
            List<Comment> comments = likeCommentDoc.getComments();
            comments.add(new Comment(request.getComment(), Utils.getTime()));
            log.info(CollectionConstants.LIKE_COMMENT + " updated successfully by " + request.getUsername());
        }
        else {
            likeCommentDoc = new LikeCommentDocImpl();
            likeCommentDoc.setUserId(request.getUserId());
            likeCommentDoc.setRecipeId(request.getRecipeId());
            likeCommentDoc.setUsername(request.getUsername());
            likeCommentDoc.setLike(request.isLike());
            List<Comment> comments = new ArrayList<>();
            comments.add(new Comment(request.getComment(), Utils.getTime()));
            likeCommentDoc.setComments(comments);
            log.info(CollectionConstants.LIKE_COMMENT + " created successfully by " + request.getUsername());
        }
        return likeCommentDocDao.save(likeCommentDoc);
    }
}
