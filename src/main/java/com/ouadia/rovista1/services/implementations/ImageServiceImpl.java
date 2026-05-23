package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.entities.Image;
import com.ouadia.rovista1.repositories.ImageRepository;
import com.ouadia.rovista1.services.interfaces.IImageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
public class ImageServiceImpl implements IImageService {

    private ImageRepository repository;

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
}
