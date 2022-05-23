package com.recipee.utils;

import com.recipee.activity.model.FileDocumentImpl;
import com.recipee.activity.model.IntentType;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public interface FileCommonUtils {

    static List<FileDocumentImpl> uploadMultipleFiles(List<MultipartFile> multipartFiles, String uploadsPath, String fileName, String folderName, IntentType intentType, String username) throws FileUploadException {
        validateFileNameAndUploadPath(uploadsPath, fileName);
        if (Utils.isNullOrEmptyCollection(multipartFiles))
            return null;
        List<FileDocumentImpl> fileDocuments = new ArrayList<>(multipartFiles.size());
        for(int i = 0; i<multipartFiles.size(); i++) {
            String temp_FileName = fileName;
            MultipartFile multipartFile= multipartFiles.get(i);
            String contentType = multipartFile.getContentType().toLowerCase();
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = originalFilename.contains(".") ? originalFilename.split("\\.")[originalFilename.split("\\.").length - 1] : "";
            if (Utils.isNullOrEmpty(extension) || (contentType != MediaType.IMAGE_JPEG_VALUE && contentType != MediaType.IMAGE_PNG_VALUE) &&
                    (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("png")))
                throw new FileUploadException("Invalid image extension!");

            String path = "/" + username + "/" + fileName + "/" + folderName;
            temp_FileName = fileName + "_" + i + "_" + Utils.getDateString() + "." + extension;
            String directory = uploadsPath + path;
            String filePath = Paths.get(directory, temp_FileName).toString();
            FileDocumentImpl imageDetails = new FileDocumentImpl(temp_FileName, username, filePath, extension, multipartFile.getSize(), contentType, intentType);
            fileDocuments.add(imageDetails);
            BufferedOutputStream stream = null;
            try {
                Files.createDirectories(Paths.get(directory));
                stream = new BufferedOutputStream(new FileOutputStream(filePath));
                stream.write(multipartFile.getBytes());
                stream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileUploadException(e.getMessage());
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return  fileDocuments;
    }

    static FileDocumentImpl upload(MultipartFile multipartFile, String uploadsPath, String fileName, String folderName, IntentType intentType, String username) throws FileUploadException, FileNotFoundException {
        validateFileNameAndUploadPath(uploadsPath, fileName);

        String contentType = multipartFile.getContentType().toLowerCase();
        String originalFilename =  multipartFile.getOriginalFilename();
        String extension = originalFilename.contains(".") ? originalFilename.split("\\.")[originalFilename.split("\\.").length - 1] : "";
        if((contentType != MediaType.IMAGE_JPEG_VALUE && contentType != MediaType.IMAGE_PNG_VALUE) &&
                Utils.isNullOrEmpty(extension) &&
                (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("png")))
            throw new FileUploadException("Invalid image extension!");

        String path = "/" + fileName + "/" + folderName;
        fileName =  fileName + "_" + Utils.getDateString() + "." + extension;
        String directory = uploadsPath + path;
        String filePath = Paths.get(directory, fileName).toString();
        FileDocumentImpl imageDetails = new FileDocumentImpl(fileName, username,filePath, extension, multipartFile.getSize(), contentType, intentType);
        BufferedOutputStream stream = null;
        try {
            Files.createDirectories(Paths.get(directory));
            stream = new BufferedOutputStream(new FileOutputStream(filePath));
            stream.write(multipartFile.getBytes());
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileUploadException(e.getMessage());
        } finally {
            if(stream!=null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
        return  imageDetails;
    }

    static void validateFileNameAndUploadPath(String uploadsPath, String fileName) {
        if (uploadsPath == null)
            throw new IllegalArgumentException("File upload path not specified");
        if (Utils.isNullOrEmpty(fileName))
            throw new IllegalArgumentException("File name not specified");
    }
    public static String randomFolder(int size) {
        return "/" + (int) (Math.random() * Math.pow(10, size));
    }
}
