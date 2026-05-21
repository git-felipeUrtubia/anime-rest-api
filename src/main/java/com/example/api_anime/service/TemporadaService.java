package com.example.api_anime.service;

import com.example.api_anime.dto.req.TemporadaReqDTO;
import com.example.api_anime.dto.res.TemporadaResDTO;
import com.example.api_anime.model.Anime;
import com.example.api_anime.model.Temporada;
import com.example.api_anime.repository.AnimeRepository;
import com.example.api_anime.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TemporadaService {

    private final TemporadaRepository temporadaRepository;
    private final AnimeRepository animeRepository;

    public TemporadaResDTO guardarTemporada(TemporadaReqDTO req) {

        if (req == null) {
            return null;
        }

        Anime anime = animeRepository.findById(req.anime_id())
                .orElseThrow(() -> new RuntimeException("Anime no encontrado con ID: " + req.anime_id()));

        boolean existeTemporada = temporadaRepository.existeTemporadaDuplicada(anime.getId(), req.nro_temporada());

        if (existeTemporada) {
            // Lanzamos una excepción que detiene el guardado
            throw new RuntimeException("El anime '" + anime.getName() + "' ya tiene registrada la temporada número " + req.nro_temporada());
        }

        Temporada temp = temporadaRepository.save(Temporada.builder()
                    .nro_temporada(req.nro_temporada())
                    .anime(anime)
                .build());



        return new TemporadaResDTO(
                temp.getId(),
                temp.getNro_temporada(),
                temp.getAnime().getName(),
                temp.getEpisodios()
        );

    }

    public List<TemporadaResDTO> listarTemporadas() {
        return temporadaRepository.findAll().stream()
                .map(temp -> new TemporadaResDTO(
                        temp.getId(),
                        temp.getNro_temporada(),
                        temp.getAnime().getName(),
                        temp.getEpisodios()
                )).toList();
    }

}
