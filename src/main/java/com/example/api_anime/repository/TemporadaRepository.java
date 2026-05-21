package com.example.api_anime.repository;

import com.example.api_anime.model.Temporada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Long> {
    @Query("SELECT COUNT(t) > 0 FROM Temporada t WHERE t.anime.id = :animeId AND t.nro_temporada = :nroTemporada")
    boolean existeTemporadaDuplicada(Long animeId, int nroTemporada);
}
