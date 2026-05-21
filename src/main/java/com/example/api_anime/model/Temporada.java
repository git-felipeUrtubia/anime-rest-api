package com.example.api_anime.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "temporada", uniqueConstraints = {
        // Esto obliga a MySQL a crear un índice único para esta combinación
        @UniqueConstraint(columnNames = {"anime_id", "nro_temporada"})
})
@Getter // Reemplaza @Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_temporada")
    private Long id;

    @Column(nullable = false)
    private int nro_temporada;

    @JoinColumn(name = "anime_id")
    @JsonBackReference("anime-temporadas")
    @ManyToOne
    Anime anime;

    @JsonManagedReference("temporada-episodio")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "temporada")
    Set<Episodio> episodios = new HashSet<>();
}
