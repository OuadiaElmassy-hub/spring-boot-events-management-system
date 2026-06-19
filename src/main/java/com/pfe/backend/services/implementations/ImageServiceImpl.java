package com.pfe.backend.services.implementations;

import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.Image;
import com.pfe.backend.exceptions.StorageProblemException;
import com.pfe.backend.repositories.ImageRepository;
import com.pfe.backend.services.interfaces.IImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements IImageService {

    private ImageRepository repository;

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public Image creatImage(Image img) {
        return repository.save(img);
    }

    @Override
    public List<String> getAllImagesUrls(List<Image> images) {
        List<String> urls = new ArrayList<>();
        for(Image img : images){
            urls.add(img.getUrl());
        }
        return urls;
    }

    /*
  * 13. Upload d'images

    Je vois :

    /api/uploads/**

    public.

    Il faut vérifier que lors de l'upload :

    extension autorisée
    taille maximale
    nom random généré
    pas de .jsp
    pas de .exe
    pas de path traversal

    Exemple :

    ../../../windows/system32

    doit être refusé.*/

    @Override
    public Evenement stockageDesImagesEvenement(Evenement evenement, List<MultipartFile> images) throws StorageProblemException {

        if (images == null || images.isEmpty()) {
            return evenement;
        }

        try {
            Path eventFolder = Paths.get(
                    uploadDir,
                    "events-images",
                    "event_num_" + evenement.getId()
            );

            // Création du dossier s'il n'existe pas
            Files.createDirectories(eventFolder);

            // Initialisation de la liste si nécessaire
            if (evenement.getImages() == null) {
                evenement.setImages(new ArrayList<>());
            }
            for (MultipartFile file : images) {

                if (file.isEmpty()) {
                    continue;
                }
                // Génération d'un nom unique
                String imageName =
                        UUID.randomUUID() + "_" +
                                file.getOriginalFilename();

                Path imagePath = eventFolder.resolve(imageName);

                // Sauvegarde physique du fichier
                Files.copy(
                        file.getInputStream(),
                        imagePath,
                        StandardCopyOption.REPLACE_EXISTING
                );
                // URL accessible depuis le frontend
                String imageUrl =
                        "/uploads/events-images/event_num_"
                                + evenement.getId()
                                + "/"
                                + imageName;

                Image image = repository.save(
                        Image.builder()
                                .nom(imageName)
                                .url(imageUrl)
                                .type(file.getContentType())
                                .evenement(evenement)
                                .build()
                );
                evenement.getImages().add(image);
            }
            return evenement;

        } catch (IOException e) {

            throw new StorageProblemException(
                    "Erreur lors du stockage des images : "
                            + e.getMessage()
            );
        }
    }


    @Override
    public void deleteImage(Long id) {
        repository.deleteById(id);
    }
}
