package com.example.api_anime.dto.res;

import com.example.api_anime.model.TypeAnime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "name",
        "image",
        "tipo"
})
public record DataOnlyAnimeResDTO(
        Long id,
        String name,
        String image,
        TypeAnime tipo
) {
}
