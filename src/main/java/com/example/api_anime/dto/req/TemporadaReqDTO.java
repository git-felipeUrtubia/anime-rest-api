package com.example.api_anime.dto.req;

import com.example.api_anime.model.Anime;
import com.example.api_anime.model.Episodio;

import java.util.Set;

public record TemporadaReqDTO(
        int nro_temporada,
        Long anime_id
) {
}
