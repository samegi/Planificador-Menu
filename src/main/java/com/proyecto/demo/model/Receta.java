package com.proyecto.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;

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
    @Column(length = 10)
    private NivelPicante nivelPicante;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<IngredienteReceta> ingredientesReceta = new ArrayList<>();

    // Agregar ingrediente a la receta
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
