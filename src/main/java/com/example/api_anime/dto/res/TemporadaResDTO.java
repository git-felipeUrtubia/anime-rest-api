package com.example.api_anime.dto.res;

import com.example.api_anime.model.Anime;
import com.example.api_anime.model.Episodio;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonPropertyOrder({
        "id",
        "nro_temporada",
        "name_anime",
        "episodios"
})
public record TemporadaResDTO(
        Long id,
        int nro_temporada,
        String name_anime,
        Set<Episodio> episodios
) {
}
