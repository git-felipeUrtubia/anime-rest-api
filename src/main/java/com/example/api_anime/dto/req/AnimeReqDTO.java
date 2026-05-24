package com.example.api_anime.dto.req;

import com.example.api_anime.model.TypeAnime;

public record AnimeReqDTO(
     String name,
     String image,
     TypeAnime tipo
) {
}
