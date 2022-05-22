package com.recipee.activity.controllers;

import com.recipee.activity.dto.LikeCommentRequest;
import com.recipee.activity.dto.RecipeDocumentDTO;
import com.recipee.activity.model.LikeCommentDocImpl;
import com.recipee.activity.model.RecipeDocumentImpl;
import com.recipee.activity.services.RecipeActivityService;
import com.recipee.jwt.config.JwtTokenUtil;
import com.recipee.rest.Response;
import com.recipee.rest.ResponseBuilder;
import com.recipee.utils.EndPointConstants;
import com.recipee.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = EndPointConstants.SLASH_RECIPE)
@AllArgsConstructor
public class RecipeActivityController implements ResponseBuilder {
    private RecipeActivityService recipeActivityService;
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public Response<List<RecipeDocumentImpl>> getAllRecipes() {
        var result = recipeActivityService.getAllRecipes();
        return buildResponse("success", result);
    }

    @GetMapping(EndPointConstants.SLASH_SEARCH)
    public Response<List<RecipeDocumentImpl>> getRecipe(@RequestParam String ingredient) {
        var result = recipeActivityService.getRecipe(ingredient);
        return buildResponse("success", result);
    }

    @PostMapping
    public Response<RecipeDocumentDTO> createRecipe(@RequestPart("recipe-data") String recipeDocumentString,
                                                    @RequestParam("attached-images") List<MultipartFile> multipartFile, @RequestHeader("Authorization") String token) throws Exception {
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        if (Utils.isNullOrEmpty(recipeDocumentString))
            throw new NullPointerException("recipe-data is empty");
        var result = recipeActivityService.createRecipe(recipeDocumentString, multipartFile, username);
        return buildResponse("success", result);
    }

    @PostMapping(EndPointConstants.SLASH_LIKE_COMMENT)
    public Response<LikeCommentDocImpl> postLikeAndComment(@RequestBody LikeCommentRequest request, @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        request.setUsername(username);
        var result = recipeActivityService.postLikeAndComment(request);
        return buildResponse("success", result);
    }

}
