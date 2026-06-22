package com.pfe.backend.services.interfaces;

import com.pfe.backend.entities.Evenement;
import com.pfe.backend.entities.Image;
import com.pfe.backend.exceptions.StorageProblemException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image creatImage(Image img);
    List<String> getAllImagesUrls(List<Image> images);
    Evenement stockageDesImagesEvenement(Evenement evenement, List<MultipartFile> images) throws StorageProblemException;
    void deleteImage(Long id);
}
