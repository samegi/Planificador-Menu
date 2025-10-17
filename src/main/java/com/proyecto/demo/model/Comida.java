package com.proyecto.demo.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;   // <-- OK

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Comida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime hora;

    // Día al que pertenece esta comida
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "dia_id")
@JsonBackReference(value = "dia-comidas")
@ToString.Exclude
private Dia dia;

// Relación muchos a uno con Receta
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "receta_id", nullable = false)
@JsonBackReference(value = "receta-comidas")
@ToString.Exclude
private Receta receta;

}
