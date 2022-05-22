package com.recipee.activity.model;

import com.recipee.activity.dto.RecipeDocumentDTO;
import com.recipee.utils.CollectionConstants;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = CollectionConstants.RECIPES)
public class RecipeDocumentImpl extends DocumentImpl {
    @Indexed(unique=true)
    private String name;
    private String owner;
    private List<Ingredient> ingredients;
    private List<String> steps;
    @DBRef()
    private List<FileDocumentImpl> attachedImages;

    public RecipeDocumentDTO toRecipeDocumentDTO() {
        RecipeDocumentDTO recipeDocumentDTO = new RecipeDocumentDTO();
        BeanUtils.copyProperties(this, recipeDocumentDTO);
        return recipeDocumentDTO;
    }
}
