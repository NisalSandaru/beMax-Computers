package com.nisal.beMax.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/products/";

    public String saveProductImage(MultipartFile file) throws IOException {
        // ✅ Validate file is not empty
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        // ✅ Validate image type (only PNG, JPG, JPEG)
        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equalsIgnoreCase("image/png") ||
                        contentType.equalsIgnoreCase("image/jpeg") ||
                        contentType.equalsIgnoreCase("image/jpg"))) {
            throw new IOException("Only PNG and JPEG images are allowed");
        }

        // ✅ Create upload directory if not exists
        File directory = new File(uploadDir);
        if (!directory.exists()) directory.mkdirs();

        // ✅ Generate clean, unique file name
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = "product_" + UUID.randomUUID() + fileExtension;

        // ✅ Save file to disk
        Path filePath = Paths.get(uploadDir + uniqueFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // ✅ Return relative URL path (for database)
        return "/uploads/products/" + uniqueFileName;
    }

    // 🔹 Helper method to extract file extension safely
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}
