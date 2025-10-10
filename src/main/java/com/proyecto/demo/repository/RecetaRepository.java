package com.proyecto.demo.repository;

import com.proyecto.demo.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}
