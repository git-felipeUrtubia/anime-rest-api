package com.example.api_anime.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "anime")
@Getter // Reemplaza @Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anime")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private TypeAnime tipo;

    @JsonManagedReference("anime-temporadas")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "anime")
    Set<Temporada> temporadas = new HashSet<>();
}
