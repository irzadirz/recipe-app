package com.recipee.activity.services;

import com.recipee.activity.dao.FileDocumentDao;
import com.recipee.activity.model.FileDocumentImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private FileDocumentDao fileDocumentDao;

    @Override
    public void createFile(FileDocumentImpl file) {

        fileDocumentDao.save(file);
    }
    @Override
    public void createMultipleFiles(List<FileDocumentImpl> file) {
        fileDocumentDao.saveAll(file);
    }
}
