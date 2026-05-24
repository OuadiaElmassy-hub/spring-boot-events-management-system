package com.ouadia.rovista1.services.interfaces;

import com.ouadia.rovista1.dtos.ImageDto;
import com.ouadia.rovista1.dtos.ImageDto;

import java.util.List;
import java.util.Map;

public interface IImageService {
    public ImageDto addImage(ImageDto imageDto);
    public ImageDto editImage(ImageDto imageDto ,Long idRech);
    public ImageDto editImageMap(Long idReche , Map<String,Object> map);
    public ImageDto getImageById(Long id);
    public List<ImageDto> getAllImages();
    public void deleteImageById(Long id);
    public void deleteAllByIds(Long ... ids);
}
