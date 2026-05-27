package com.example.api_anime.service;

import com.example.api_anime.client.bunny.BunnyClientService;
import com.example.api_anime.client.bunny.BunnyVideoResponse;
import com.example.api_anime.dto.req.EpisodioReqDTO;
import com.example.api_anime.dto.res.EpisodioResDTO;
import com.example.api_anime.model.Anime;
import com.example.api_anime.model.Episodio;
import com.example.api_anime.model.Temporada;
import com.example.api_anime.repository.AnimeRepository;
import com.example.api_anime.repository.EpisodioRepository;
import com.example.api_anime.repository.TemporadaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EpisodioService {

    private final EpisodioRepository episodioRepository;
    private final AnimeRepository animeRepository;
    private final BunnyClientService bunnyClientService;

    public EpisodioResDTO guardarEpisodio(EpisodioReqDTO req) {

        if (req == null) {
            return null;
        }

        Anime anime = animeRepository.findById(req.anime_id())
                .orElseThrow(()  -> new RuntimeException("Anime con ID " + req.anime_id() + " no existe"));

        Temporada temp = anime.getTemporadas().stream()
                .filter(t -> t.getNro_temporada() == req.nro_temporada())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Numero de temporada no encontrada"));

        boolean existeEpisodio = episodioRepository.existeEpisodioDuplicado(req.nro_episodio(), temp.getId());

        if (existeEpisodio) {
            // Lanzamos una excepción que detiene el guardado
            throw new RuntimeException("El episodio '" + req.nro_episodio() + "' ya tiene registro la temporada número " + req.nro_temporada());
        }

        Episodio ep = episodioRepository.save(Episodio.builder()
                        .title(req.title())
                        .nro_episodio(req.nro_episodio())
                        .uri(req.uri())
                        .temporada(temp)
                .build());

        return new EpisodioResDTO(
                ep.getId(),
                ep.getTitle(),
                ep.getNro_episodio(),
                ep.getUri(),
                ep.getTemporada().getNro_temporada(),
                ep.getTemporada().getAnime().getId()
        );

    }

    public List<EpisodioResDTO> listarEpisodios() {
        return episodioRepository.findAll().stream()
                .map(ep -> new EpisodioResDTO(
                        ep.getId(),
                        ep.getTitle(),
                        ep.getNro_episodio(),
                        ep.getUri(),
                        ep.getTemporada().getNro_temporada(),
                        ep.getTemporada().getAnime().getId()
                )).toList();
    }

    public List<EpisodioResDTO> listarEpisodiosPorTemporada(Long anime_id, int nro_temporada) {

        return episodioRepository.findByTemporada(anime_id, nro_temporada).stream()
                .map(ep -> new EpisodioResDTO(
                        ep.getId(),
                        ep.getTitle(),
                        ep.getNro_episodio(),
                        ep.getUri(),
                        ep.getTemporada().getNro_temporada(),
                        ep.getTemporada().getAnime().getId()
                )).toList();
    }

    public void sincronizarEpisodio(Long animeId, int nro_temp, String libraryId, String collectionId, String apiKey) {
        List<BunnyVideoResponse> videosBunny = bunnyClientService.listarVideos(libraryId, collectionId, apiKey);

        if (videosBunny == null || videosBunny.isEmpty()) {
            throw new RuntimeException("No se encontraron videos en la colección de Bunny.net");
        }

        List<Episodio> newEp = new ArrayList<>();

        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new RuntimeException("Anime con ID " + animeId + " no existe"));

        Temporada temp = anime.getTemporadas().stream()
                .filter(t -> t.getNro_temporada() == nro_temp)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Numero de temporada no encontrada"));

        videosBunny.forEach(video -> {

            String titleVideo = video.getTitle();

            System.out.println("TituloVideo: " + titleVideo);

            int nroEpVideo = Integer.parseInt(titleVideo.replace("Episodio ", "").trim());
            String uriM3u8 = "https://vz-0ce7c9d0-8e0.b-cdn.net/" + video.getGuid() + "/playlist.m3u8";

            newEp.add(Episodio.builder()
                    .title(titleVideo)
                    .nro_episodio(nroEpVideo)
                    .uri(uriM3u8)
                    .temporada(temp)
                    .build());
        });

        episodioRepository.saveAll(newEp);

    }


}
