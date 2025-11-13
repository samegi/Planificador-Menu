package com.proyecto.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Macronutriente macronutriente;

    // Relación con IngredienteReceta (se mantiene como estaba)
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference        // lado "padre" de la relación Receta - IngredienteReceta
    @ToString.Exclude
    private List<IngredienteReceta> ingredientesReceta = new ArrayList<>();

    // Relación con Comida: NO la mandamos en el JSON para evitar ciclos
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore                  // <- evita Receta -> comidas -> receta -> ...
    @ToString.Exclude
    private List<Comida> comidas = new ArrayList<>();

    // Agregar ingrediente
    public void addIngrediente(Ingrediente ingrediente, float cantidad) {
        IngredienteReceta ir = new IngredienteReceta();
        ir.setReceta(this);
        ir.setIngrediente(ingrediente);
        ir.setCantidad(cantidad);
        ingredientesReceta.add(ir);
    }

    // Eliminar ingrediente
    public void removeIngrediente(Ingrediente ingrediente) {
        ingredientesReceta.removeIf(ir -> ir.getIngrediente().equals(ingrediente));
    }
}
