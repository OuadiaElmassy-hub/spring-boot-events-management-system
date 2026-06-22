package com.pfe.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Set;
import java.util.UUID;

/**
 * Stockage local des fichiers uploadés (icônes de catégories, avatars, etc.)
 * Fichiers servis via "/uploads/**" → configuration dans WebMvcConfig.
 *
 * En production, remplacez par une implémentation S3/MinIO.
 */
@Service
public class FileStorageService {

    // Configurable dans application.properties : app.upload.dir=./uploads
    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    // URL de base publique exposée par Spring (voir WebMvcConfig)
    @Value("${app.upload.url-prefix:/uploads}")
    private String urlPrefix;

    private static final Set<String> ALLOWED_TYPES = Set.of(
        "image/png", "image/jpeg", "image/jpg",
        "image/svg+xml", "image/webp", "image/gif"
    );
    private static final long MAX_SIZE = 2L * 1024L * 1024L; // 2 Mo

    /**
     * Sauvegarde un fichier image dans le répertoire configuré.
     * @return URL publique du fichier
     */
    public String store(MultipartFile file, String subDir) throws IOException {
        validateFile(file);

        Path dir = Paths.get(uploadDir, subDir);
        Files.createDirectories(dir);

        String ext      = getExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + ext;
        Path   target   = dir.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return urlPrefix + "/" + subDir + "/" + filename;
    }

    /**
     * Supprime un fichier à partir de son URL publique.
     * Si l'URL est externe (https://...) on ne fait rien.
     */
    public void delete(String publicUrl) {
        if (publicUrl == null || !publicUrl.startsWith(urlPrefix)) return;
        String relative = publicUrl.substring(urlPrefix.length());
        try {
            Path file = Paths.get(uploadDir + relative);
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
            // Pas bloquant
        }
    }

    // ── Validation ────────────────────────────────────────────────
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("Fichier vide ou absent");
        if (!ALLOWED_TYPES.contains(file.getContentType()))
            throw new IllegalArgumentException("Type de fichier non autorisé : " + file.getContentType());
        if (file.getSize() > MAX_SIZE) {
            System.out.println("Size: " + file.getSize() + " | MAX: " + MAX_SIZE); // debug
            throw new IllegalArgumentException("Fichier trop volumineux (max 2 Mo)");
        }
    }

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "bin";
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }
}