package com.recipee.activity.dto;

import com.recipee.activity.model.FileDocumentImpl;
import com.recipee.activity.model.Ingredient;
import com.recipee.utils.Utils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
@Data
public class RecipeDocumentDTO {
    public String id;
    public String name;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<FileDocumentImpl> attachedImages;
    private long cts;
    private long uts;


}
