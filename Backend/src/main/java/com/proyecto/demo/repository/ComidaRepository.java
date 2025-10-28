package com.proyecto.demo.repository;

import com.proyecto.demo.model.Comida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComidaRepository extends JpaRepository<Comida, Long> {

    //obtiene todas las comidas que usan una receta específica
    List<Comida> findByRecetaId(Long recetaId);
}


