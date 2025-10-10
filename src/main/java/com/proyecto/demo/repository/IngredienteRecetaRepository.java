package com.proyecto.demo.repository;

import com.proyecto.demo.model.IngredienteReceta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredienteRecetaRepository extends JpaRepository<IngredienteReceta, Long> {

    List<IngredienteReceta> findByRecetaId(Long recetaId);

    List<IngredienteReceta> findByIngredienteId(Long ingredienteId);

    boolean existsByRecetaIdAndIngredienteId(Long recetaId, Long ingredienteId);
}
