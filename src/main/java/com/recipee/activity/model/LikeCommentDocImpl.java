package com.recipee.activity.model;

import com.recipee.utils.CollectionConstants;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = CollectionConstants.LIKE_COMMENT)
@Data
public class LikeCommentDocImpl extends DocumentImpl{
    private String username;
    private String userId;
    private String recipeId;
    private boolean isLike;
    private List<Comment> comments;
}
