package com.ouadia.rovista1.controllers;


import com.ouadia.rovista1.services.interfaces.IFavorieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/favorie")
@AllArgsConstructor
public class FavorieContoller {
    IFavorieService favorieService;


}
