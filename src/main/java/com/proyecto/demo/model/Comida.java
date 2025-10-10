package com.proyecto.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
public class Comida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Hora del día (por ejemplo: 07:30, 12:00, 18:45)
    @Column(nullable = false)
    private LocalTime hora;

    // Relación muchos a uno con Receta
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receta_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Receta receta;

}
