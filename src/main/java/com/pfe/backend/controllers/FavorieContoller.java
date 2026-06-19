package com.pfe.backend.controllers;


import com.pfe.backend.services.interfaces.IFavorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorie")
@AllArgsConstructor
public class FavorieContoller {
    IFavorieService favorieService;


}
