package com.proyecto.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class IngredienteReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_receta")
    private Receta receta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(nullable = false)
    private double cantidad;
}
