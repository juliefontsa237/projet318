package com.example.GestionCours.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public class FileUtil {

    private static final String UPLOAD_DIR = "uploads/";

    public static String saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            File dest = new File(UPLOAD_DIR + fileName);
            file.transferTo(dest);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement du fichier", e);
        }
    }
}
