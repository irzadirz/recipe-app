package com.recipee.activity.controllers;

import com.recipee.utils.EndPointConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;


class RecipeActivityControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;


    @Test
    void getAllRecipesWithoutJwtReturnUnauthorized() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get(EndPointConstants.SLASH_RECIPE));
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void getAllRecipesWithJwtReturnSuccess() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+BaseIntegrationTest.jwt);
        var result = mvc.perform(MockMvcRequestBuilders.get(EndPointConstants.SLASH_RECIPE)
                .headers(headers));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

   /* @Test
    void createRecipe() {
    }

    @Test
    void postLikeAndComment() {
    }*/

}