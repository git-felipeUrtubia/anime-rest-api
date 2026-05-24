package com.example.api_anime.controller;


import com.example.api_anime.dto.req.EpisodioReqDTO;
import com.example.api_anime.service.EpisodioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/episodio")
public class EpisodioController {

    private final EpisodioService episodioService;

    @PostMapping
    public ResponseEntity<?> guardarEpisodio(@RequestBody EpisodioReqDTO req) {
        try {
            return new ResponseEntity<>(episodioService.guardarEpisodio(req), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> listarEpisodio() {
        try {
            return new ResponseEntity<>(episodioService.listarEpisodios(), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id_anime}/{nro_temporada}")
    public ResponseEntity<?> listarEpisodioByTemporada(@PathVariable Long id_anime, @PathVariable int nro_temporada) {
        try {
            return new ResponseEntity<>(episodioService.listarEpisodiosPorTemporada(id_anime, nro_temporada), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
