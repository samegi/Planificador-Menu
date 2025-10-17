// src/main/java/com/proyecto/demo/service/RecetaService.java
package com.proyecto.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import com.proyecto.demo.repository.RecetaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final IngredienteRecetaRepository ingredienteRecetaRepository;

    public RecetaService(RecetaRepository recetaRepository, IngredienteRecetaRepository ingredienteRecetaRepository) {
        this.recetaRepository = recetaRepository;
        this.ingredienteRecetaRepository = ingredienteRecetaRepository;
    }

    public Receta crearReceta(Receta receta) {
        if (recetaRepository.existsByNombreIgnoreCase(receta.getNombre())) {
            throw new IllegalArgumentException("Ya existe una receta con el nombre: " + receta.getNombre());
        }
        if (receta.getIngredientesReceta() != null) {
            Set<Long> ids = new HashSet<>();
            for (IngredienteReceta ir : receta.getIngredientesReceta()) {
                Long ingredienteId = ir.getIngrediente() != null ? ir.getIngrediente().getId() : null;
                if (ingredienteId != null && !ids.add(ingredienteId)) {
                    throw new IllegalArgumentException(
                        "El ingrediente con ID " + ingredienteId + " está repetido en la receta."
                    );
                }
                ir.setReceta(receta);
            }
        }
        return recetaRepository.save(receta);
    }

    public List<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }

    public Receta obtenerReceta(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con ID: " + id));
    }

   // RecetaService.java
public Receta actualizarReceta(Long id, Receta recetaActualizada) {
    Receta receta = obtenerReceta(id);
    receta.setNombre(recetaActualizada.getNombre());
    receta.setDescripcion(recetaActualizada.getDescripcion());

    // NO pises con null
    if (recetaActualizada.getMacronutriente() != null) {
        receta.setMacronutriente(recetaActualizada.getMacronutriente());
    }

    if (recetaActualizada.getIngredientesReceta() != null) {
        receta.getIngredientesReceta().clear();
        recetaActualizada.getIngredientesReceta().forEach(ir -> {
            ir.setReceta(receta);
            receta.getIngredientesReceta().add(ir);
        });
    }
    return recetaRepository.save(receta);
}

    public void eliminarReceta(Long id) {
        Receta receta = obtenerReceta(id);
        recetaRepository.delete(receta);
    }
}
