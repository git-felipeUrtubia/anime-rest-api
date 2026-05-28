package com.example.api_anime.dto.res;

import com.example.api_anime.model.Anime;
import com.example.api_anime.model.Episodio;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonPropertyOrder({
        "id_temporada",
        "nro_temporada",
        "id_anime"
})
public record TemporadaResDTO(
        Long id_temporada,
        int nro_temporada,
        Long id_anime
) {
}
