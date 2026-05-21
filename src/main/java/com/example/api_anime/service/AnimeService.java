package com.example.api_anime.service;

import com.example.api_anime.dto.req.AnimeReqDTO;
import com.example.api_anime.dto.res.AnimeResDTO;
import com.example.api_anime.model.Anime;
import com.example.api_anime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository animeRepository;

    public AnimeResDTO guardarAnime(AnimeReqDTO req) {

        if (req == null) {
            return null;
        }

        Anime anime = animeRepository.save(Anime.builder()
                    .name(req.name())
                    .image(req.image())
                .build());

        return new AnimeResDTO(
                anime.getId(),
                anime.getName(),
                anime.getImage(),
                anime.getTemporadas()
        );

    }

    public List<AnimeResDTO> listarAnimes() {

        return animeRepository.findAll().stream()
                .map(anime -> new AnimeResDTO(
                        anime.getId(),
                        anime.getName(),
                        anime.getImage(),
                        anime.getTemporadas()
                )).toList();

    }


}
