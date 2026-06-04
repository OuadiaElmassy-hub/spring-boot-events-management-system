package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.entities.Categorie;
import com.ouadia.rovista1.entities.Evenement;
import com.ouadia.rovista1.entities.Image;
import com.ouadia.rovista1.exceptions.StorageProblemException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image creatImage(Image img);
    List<String> getAllImagesUrls(List<Image> images);
    Evenement stockageDesImagesEvenement(Evenement evenement, List<MultipartFile> images) throws StorageProblemException;
    Categorie stockageDesImagesCategorie(Categorie categorie, MultipartFile image) throws StorageProblemException;
    void deleteImage(Long id);
}
