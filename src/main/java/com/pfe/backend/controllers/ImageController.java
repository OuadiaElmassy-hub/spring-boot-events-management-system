package com.pfe.backend.controllers;



import com.pfe.backend.entities.Image;
import com.pfe.backend.exceptions.ImageNotFoundException;
import com.pfe.backend.exceptions.ReservationNotFoundException;
import com.pfe.backend.exceptions.UserNotFoundException;
import com.pfe.backend.services.implementations.ImageServiceImpl;
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
