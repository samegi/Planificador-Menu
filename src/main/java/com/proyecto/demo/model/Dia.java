package com.proyecto.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Dia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    // Un día tiene muchas comidas
    @OneToMany(mappedBy = "dia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "dia-comidas")
    @ToString.Exclude
    private List<Comida> comidas = new ArrayList<>();

    // Helpers opcionales (no obligatorios)
    public void agregarComida(Comida comida) {
        comidas.add(comida);
        comida.setDia(this);
    }
    public void removerComida(Comida comida) {
        comidas.remove(comida);
        comida.setDia(null);
    }
}
