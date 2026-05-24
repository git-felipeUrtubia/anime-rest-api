package com.example.api_anime.repository;

import com.example.api_anime.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long> {

    @Query("SELECT COUNT(e) > 0 FROM Episodio e WHERE e.nro_episodio = :nro_episodio AND e.temporada.id = :temporada_id")
    boolean existeEpisodioDuplicado(int nro_episodio, Long temporada_id);

    @Query("SELECT e FROM Episodio e WHERE e.temporada.anime.id = :animeId AND e.temporada.nro_temporada = :nro_temporada ")
    List<Episodio> findByTemporada(@Param("animeId") Long animeId, @Param("nro_temporada") int nro_temporada);
}
