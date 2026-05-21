package com.example.api_anime.dto.req;


public record EpisodioReqDTO(
        String title,
        int nro_episodio,
        String uri,
        int nro_temporada,
        Long anime_id
) {
}
