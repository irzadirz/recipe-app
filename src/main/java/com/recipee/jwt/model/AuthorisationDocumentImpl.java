package com.recipee.jwt.model;

import com.recipee.activity.model.FileDocumentImpl;
import com.recipee.utils.CollectionConstants;
import com.recipee.utils.Utils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = CollectionConstants.USERS)
public class AuthorisationDocumentImpl {
    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    private UserType userType = UserType.USER;
    @DBRef
    private FileDocumentImpl profileImageDoc;
    private long uts = Utils.getTime();
    private long cts = Utils.getTime();
}
