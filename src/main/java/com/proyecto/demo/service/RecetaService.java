package com.proyecto.demo.service;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final IngredienteRecetaRepository ingredienteRecetaRepository;

    public RecetaService(RecetaRepository recetaRepository, IngredienteRecetaRepository ingredienteRecetaRepository) {
        this.recetaRepository = recetaRepository;
        this.ingredienteRecetaRepository = ingredienteRecetaRepository;
    }

    // Crear receta con ingredientes asociados
    public Receta crearReceta(Receta receta) {
        if (recetaRepository.existsByNombreIgnoreCase(receta.getNombre())) {
            throw new IllegalArgumentException("Ya existe una receta con el nombre: " + receta.getNombre());
        }

        // Asegurar relaciones bidireccionales
        if (receta.getIngredientesReceta() != null) {
            receta.getIngredientesReceta().forEach(ir -> ir.setReceta(receta));
        }

        return recetaRepository.save(receta);
    }

    // Listar todas
    public List<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }

    // Obtener una receta
    public Receta obtenerReceta(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con ID: " + id));
    }

    // Actualizar receta
    public Receta actualizarReceta(Long id, Receta recetaActualizada) {
        Receta receta = obtenerReceta(id);
        receta.setNombre(recetaActualizada.getNombre());
        receta.setDescripcion(recetaActualizada.getDescripcion());
        receta.setNivelPicante(recetaActualizada.getNivelPicante());

        // Actualizar ingredientes si vienen en el cuerpo
        if (recetaActualizada.getIngredientesReceta() != null) {
            receta.getIngredientesReceta().clear();
            recetaActualizada.getIngredientesReceta().forEach(ir -> {
                ir.setReceta(receta);
                receta.getIngredientesReceta().add(ir);
            });
        }

        return recetaRepository.save(receta);
    }

    // Eliminar receta y sus relaciones
    public void eliminarReceta(Long id) {
        Receta receta = obtenerReceta(id);
        recetaRepository.delete(receta);
    }
}
