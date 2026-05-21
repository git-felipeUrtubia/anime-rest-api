package com.example.api_anime.controller;

import com.example.api_anime.dto.req.AnimeReqDTO;
import com.example.api_anime.model.Anime;
import com.example.api_anime.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/anime")
public class AnimeController {

    private final AnimeService animeService;

    @PostMapping
    public ResponseEntity<?> guardarAnime(@RequestBody AnimeReqDTO req) {
        try {
            return new ResponseEntity<>(animeService.guardarAnime(req), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> listarAnimes() {
        try {
            return new ResponseEntity<>(animeService.listarAnimes(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
