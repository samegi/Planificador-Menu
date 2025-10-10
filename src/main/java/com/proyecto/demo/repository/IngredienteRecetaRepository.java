package com.proyecto.demo.repository;

import com.proyecto.demo.model.IngredienteReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRecetaRepository extends JpaRepository<IngredienteReceta, Long> {
}

