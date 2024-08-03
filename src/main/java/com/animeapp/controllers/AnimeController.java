package com.animeapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.animeapp.models.Anime;
import com.animeapp.services.AnimeService;

import java.util.List;

@RestController
@RequestMapping("/")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping("/list")
    public ResponseEntity<List<Anime>> getAnimeList() {
        List<Anime> animeList = animeService.getAnimeList();
        return new ResponseEntity<>(animeList, HttpStatus.OK);
    }

    @GetMapping("/score")
    public String getAverageScore() {
        return animeService.calculateAverageScore();
    }

}

