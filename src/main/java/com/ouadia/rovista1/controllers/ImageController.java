package com.ouadia.rovista1.controllers;



import com.ouadia.rovista1.dtos.image.ImageRequestDto;
import com.ouadia.rovista1.dtos.image.ImageResponseDto;
import com.ouadia.rovista1.entities.Image;
import com.ouadia.rovista1.exceptions.ImageNotFoundException;
import com.ouadia.rovista1.exceptions.ReservationNotFoundException;
import com.ouadia.rovista1.exceptions.UserNotFoundException;
import com.ouadia.rovista1.services.implementations.ImageServiceImpl;
import com.ouadia.rovista1.services.interfaces.IImageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {
    final ImageServiceImpl imageService;

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) throws ImageNotFoundException, ReservationNotFoundException, UserNotFoundException {

        return new ResponseEntity<>((imageService.creatImage(image)), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<String>> GetAllImageUrl(@RequestBody List<Image> images) throws ImageNotFoundException {
        return ResponseEntity.ok(imageService.getAllImagesUrls(images));
    }


}
