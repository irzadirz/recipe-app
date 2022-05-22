package com.recipee.activity.services;

import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;


@Configuration
@ConfigurationProperties(prefix = "files.props")
@Data
public class FileProperties {
    private String uploadPath;
    private String profileImageFolder;
    private String attachmentsFolder;
}
