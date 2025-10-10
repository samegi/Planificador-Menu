package com.proyecto.demo.service;

import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.repository.IngredienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    // Crear nuevo ingrediente
    public Ingrediente crearIngrediente(Ingrediente ingrediente) {
        // Validar duplicados por nombre (opcional pero recomendable)
        if (ingredienteRepository.existsByNombreIgnoreCase(ingrediente.getNombre())) {
            throw new IllegalArgumentException("Ya existe un ingrediente con el nombre: " + ingrediente.getNombre());
        }
        return ingredienteRepository.save(ingrediente);
    }

    // Obtener todos los ingredientes
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }

    // Buscar un ingrediente por ID
    public Ingrediente obtenerIngredientePorId(Long id) {
        return ingredienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado con ID: " + id));
    }

    // Actualizar ingrediente
    public Ingrediente actualizarIngrediente(Long id, Ingrediente ingredienteActualizado) {
        Ingrediente existente = obtenerIngredientePorId(id);
        existente.setNombre(ingredienteActualizado.getNombre());
        return ingredienteRepository.save(existente);
    }

    // Eliminar ingrediente y sus relaciones
    public void eliminarIngrediente(Long id) {
        Ingrediente ingrediente = obtenerIngredientePorId(id);

        // Si hay relaciones con recetas, se eliminan por cascada
        if (ingrediente.getRecetas() != null) {
            ingrediente.getRecetas().clear();
        }

        ingredienteRepository.delete(ingrediente);
    }
}
