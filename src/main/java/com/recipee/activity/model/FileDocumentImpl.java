package com.recipee.activity.model;

import com.recipee.utils.CollectionConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = CollectionConstants.FILES)
@NoArgsConstructor
public class FileDocumentImpl extends DocumentImpl{
    private String owner;
    private String filename;
    private String path;
    private String description;
    private String extension;
    private Long size;
    private String mediaType;
    private IntentType intentType;

    public FileDocumentImpl(String filename, String owner, String path, String extension, Long size, String mediaType, IntentType intentType) {
        this.filename = filename;
        this.owner = owner;
        this.path = path;
        this.extension = extension;
        this.size = size;
        this.mediaType = mediaType;
        this.intentType = intentType;
    }
}
