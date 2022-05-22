package com.recipee.activity.services;

import com.recipee.activity.model.FileDocumentImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void createFile(FileDocumentImpl file);
    void createMultipleFiles(List<FileDocumentImpl> file);
}
