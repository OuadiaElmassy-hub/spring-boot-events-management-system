package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.entities.Image;

import java.util.List;

public interface IImageService {
    Image creatImage(Image img);
    List<String> getAllImagesUrls(List<Image> images);
}
