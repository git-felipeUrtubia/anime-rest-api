package com.example.api_anime.dto.res;

import com.example.api_anime.model.Temporada;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "title",
        "nro_episodio",
        "uri",
        "nro_temporada",
        "anime_id"
})
public record EpisodioResDTO(
        Long id,
        String title,
        int nro_episodio,
        String uri,
        int nro_temporada,
        Long anime_id
) {
}
