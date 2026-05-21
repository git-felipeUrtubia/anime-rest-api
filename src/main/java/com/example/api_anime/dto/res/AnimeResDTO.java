package com.example.api_anime.dto.res;

import com.example.api_anime.model.Temporada;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Set;

@JsonPropertyOrder({
        "id",
        "name",
        "image",
        "temporadas"
})
public record AnimeResDTO(
        Long id,
        String name,
        String image,
        Set<Temporada> temporadas
) {
}
