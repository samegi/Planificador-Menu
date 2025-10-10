package com.proyecto.demo.service;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import com.proyecto.demo.repository.IngredienteRepository;
import com.proyecto.demo.repository.RecetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class IngredienteRecetaService {

    private final IngredienteRecetaRepository ingredienteRecetaRepository;
    private final RecetaRepository recetaRepository;
    private final IngredienteRepository ingredienteRepository;

    public IngredienteRecetaService(IngredienteRecetaRepository ingredienteRecetaRepository, RecetaRepository recetaRepository, IngredienteRepository ingredienteRepository) {
        this.ingredienteRecetaRepository = ingredienteRecetaRepository;
        this.recetaRepository = recetaRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    // Crear nueva relación con validación de duplicado
    public IngredienteReceta crearIngredienteReceta(Long idReceta, Long idIngrediente, float cantidad) {
        Receta receta = recetaRepository.findById(idReceta)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con ID: " + idReceta));

        Ingrediente ingrediente = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado con ID: " + idIngrediente));

        // Validar duplicado
        if (ingredienteRecetaRepository.existsByRecetaIdAndIngredienteId(idReceta, idIngrediente)) {
            throw new IllegalArgumentException(
                    "El ingrediente '" + ingrediente.getNombre() + "' ya está asociado a la receta '" + receta.getNombre() + "'."
            );
        }

        // Crear nueva relación
        IngredienteReceta relacion = new IngredienteReceta();
        relacion.setReceta(receta);
        relacion.setIngrediente(ingrediente);
        relacion.setCantidad(cantidad);

        return ingredienteRecetaRepository.save(relacion);
    }

    // --- Métodos restantes sin cambios ---
    public List<IngredienteReceta> listarTodas() {
        return ingredienteRecetaRepository.findAll();
    }

    public IngredienteReceta obtenerPorId(Long id) {
        return ingredienteRecetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación no encontrada con ID: " + id));
    }

    public List<IngredienteReceta> buscarPorReceta(Long recetaId) {
        return ingredienteRecetaRepository.findByRecetaId(recetaId);
    }

    public List<IngredienteReceta> buscarPorIngrediente(Long ingredienteId) {
        return ingredienteRecetaRepository.findByIngredienteId(ingredienteId);
    }

    public IngredienteReceta actualizarCantidad(Long id, float nuevaCantidad) {
        IngredienteReceta ir = obtenerPorId(id);
        ir.setCantidad(nuevaCantidad);
        return ingredienteRecetaRepository.save(ir);
    }

    public void eliminar(Long id) {
        IngredienteReceta ir = obtenerPorId(id);
        ingredienteRecetaRepository.delete(ir);
    }
}
