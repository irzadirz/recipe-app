package com.recipee.activity.dto;

import lombok.Data;

@Data
public class LikeCommentRequest {
    private String userId;
    private String recipeId;
    private String username;
    private boolean like;
    private String comment;
}
