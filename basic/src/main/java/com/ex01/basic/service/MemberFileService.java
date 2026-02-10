package com.ex01.basic.service;

import com.ex01.basic.exception.FileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MemberFileService {
    private static final String DIR = "uploads/";

    public String saveFile(MultipartFile multipartFile) {
        String fileName = null;
        if (multipartFile == null || multipartFile.isEmpty())
            fileName = "nan";
        else {
            fileName = UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename();
            Path path = Paths.get(DIR + fileName);
            try {
                Files.createDirectories(path.getParent());
                multipartFile.transferTo(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return fileName;
    }

    public byte[] getImage(String fileName) {
        Path filePath = Paths.get(DIR + fileName);
        if (!Files.exists((filePath)))
            throw new FileNotFoundException("파일이 존재하지 않음");
        byte[] imageBytes = {0};
        try {
            imageBytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageBytes;
    }

    public void deleteFile(String fileName) {
        Path path = Paths.get(DIR + fileName);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
