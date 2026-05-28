package com.example.api_anime.controller;

import com.example.api_anime.dto.req.TemporadaReqDTO;
import com.example.api_anime.service.TemporadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/temporada")
public class TemporadaController {

    private final TemporadaService temporadaService;

    @PostMapping
    public ResponseEntity<?> guardarTemporada(@RequestBody TemporadaReqDTO req) {
        try {
            return new ResponseEntity<>(temporadaService.guardarTemporada(req), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> listarTemporadas() {
        try {
            return new ResponseEntity<>(temporadaService.listarTemporadas(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by_anime")
    public ResponseEntity<?> listarTemporadasPorAnime(@RequestParam Long anime_id) {
        try {
            return new ResponseEntity<>(temporadaService.listarTemporadasPorAnime(anime_id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
