package com.example.api_anime.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "episodio", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nro_episodio", "temporada_id"})
})
@Getter // Reemplaza @Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_episodio")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int nro_episodio;

    @Column(nullable = false)
    private String uri;

    @JsonBackReference("temporada-episodio")
    @JoinColumn(name = "temporada_id")
    @ManyToOne
    Temporada temporada;
}
