package com.brandingku.web.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadHelper {

    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        // mkdir if not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        }

        // Normalize file name
        String sanitizedFileName = sanitizeFileName(originalFileName);
        String fileName = sanitizedFileName + "_" + UUID.randomUUID() + fileExtension;
        Path filePath = uploadPath.resolve(fileName);

        // Copy file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }

    /**
     * public String saveUrlBanner(MultipartFile file) throws IOException {
     * log.info("baseUrl save banner is : {}", baseUrl);
     * return FileUploadHelper.urlSaveFile(file, UPLOAD_DIR + "/event/banner", baseUrl);
     * }
     */
    public static String urlSaveFile(MultipartFile file, String uploadDir, String baseUrl) throws IOException {
        String path = saveFile(file, uploadDir);
        String updatePath = path.replaceAll("\\\\", "/");

        return baseUrl + GlobalConverter.replaceImagePath(updatePath);
    }

    /**
     * Change symbol in file name
     */
    private static String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return "default";
        }
        // convert character
        return fileName.replaceAll("[^a-zA-Z0-9 ]", "") // remove symbol without space
                .replaceAll(" +", "_")
                .toLowerCase();
    }

    public static void deleteFile(String filePath, String UPLOAD_DIR) {
        try {
            Path path = Paths.get(filePath.replaceAll("/uploads", UPLOAD_DIR));
            if (Files.exists(path)) {
                Files.delete(path); // Delete the file if it exists
                System.out.println("File deleted: " + path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error deleting file: " + filePath, e);
        }
    }

}
