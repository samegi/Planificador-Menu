package com.proyecto.demo.repository;

import com.proyecto.demo.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
