package com.atom.application.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    public void uploadFile(MultipartFile file, Long id) throws IOException {
        String folderPathStr = "src/main/resources/img/pid" + id.toString();
        Path folderPath = Paths.get(folderPathStr);
        boolean folderExists = Files.exists(folderPath);
        if (folderExists == false) {
            Files.createDirectories(folderPath);
        }
        Path destPath = folderPath.resolve("image.jpg");
        Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

    }

}
