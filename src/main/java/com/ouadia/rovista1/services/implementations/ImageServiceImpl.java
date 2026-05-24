package com.ouadia.rovista1.services.implementations;

import com.ouadia.rovista1.Mapper.*;
import com.ouadia.rovista1.Mapper.ImageMapper;
import com.ouadia.rovista1.Mapper.ImageMapper;
import com.ouadia.rovista1.Mapper.ImageMapper;
import com.ouadia.rovista1.dtos.ImageDto;
import com.ouadia.rovista1.entities.*;
import com.ouadia.rovista1.entities.Image;
import com.ouadia.rovista1.entities.enums.TypeBillet;
import com.ouadia.rovista1.entities.enums.TypePhoto;
import com.ouadia.rovista1.exceptions.ImageNotFoundException;
import com.ouadia.rovista1.repositories.ImageRepository;
import com.ouadia.rovista1.services.interfaces.IImageService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
public class ImageServiceImpl implements IImageService {
  private ImageRepository repository;
    @Override
    public ImageDto addImage(ImageDto imageDto) {
        Image image= ImageMapper.mapToImage(imageDto);
        if (repository.findById(image.getId()).isPresent()){
            throw new RuntimeException(" image exsist ");
        }else
            return ImageMapper.mapToImageDto(repository.save(image));
    }

    @Override
    public ImageDto editImage(ImageDto imageDto, Long idRech) {
        Image image= ImageMapper.mapToImage(imageDto);
        if (image== null) return null;
        else {
            Image image1 = repository.findById(idRech).get();
            if (image == null) {
                return null;
            }
            image1.setNom(image.getNom());
            image1.setUrl(image.getUrl());
            image1.setType(image.getType());
            image1.setEvenement(image.getEvenement());
            return ImageMapper.mapToImageDto (repository.save(image1));
        }
    }

    @Override
    public ImageDto editImageMap(Long idReche, Map<String, Object> map) {
        if (map==null){return null;}
        Image image =repository.findById(idReche).get();
        if (image == null) {
            return null;
        }
        if (map.containsKey("nom")) {
            image.setNom((String) map.get("nom"));
        }
        if (map.containsKey("url")) {
            image.setUrl((String) map.get("url"));
        }
        if (map.containsKey("type")) {
            image.setType(TypePhoto.valueOf (map.get("type").toString()));
        }
        if (map.containsKey("evenement")) {
            image.setEvenement((Evenement)map.get("evenement"));
        }
        return ImageMapper.mapToImageDto(repository.save(image));
    }

    @Override
    public ImageDto getImageById(Long id) {
        Image image = repository.findById(id).orElseThrow(() -> new ImageNotFoundException("image not found"));
        return ImageMapper.mapToImageDto(image);
    }

    @Override
    public List<ImageDto> getAllImages() {
        return repository.findAll().stream().map(image -> ImageMapper.mapToImageDto(image)).toList();
    }

    @Override
    public void deleteImageById(Long id) {
     repository.deleteById(id);
    }

    @Override
    public void deleteAllByIds(Long... ids) {
        for (Long id :ids){
            deleteImageById(id);
        }
    }
}
