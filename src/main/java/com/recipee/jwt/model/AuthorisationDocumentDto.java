package com.recipee.jwt.model;

import lombok.Data;

@Data
public class AuthorisationDocumentDto {
    private String name;
    private String username;
    private String password;
}
