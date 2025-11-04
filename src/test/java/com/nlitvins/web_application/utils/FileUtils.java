package com.nlitvins.web_application.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class FileUtils {
    public static String getTextFromResource(String path) {
        try {
            return Files.readString(Paths.get(FileUtils.class.getResource(path).toURI()), StandardCharsets.UTF_8);
        } catch (IOException | java.net.URISyntaxException e) {
            throw new RuntimeException("Failed to read resource: " + path, e);
        }
    }
} 