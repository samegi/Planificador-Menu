package com.proyecto.demo.service;

import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.repository.IngredienteRepository;
import com.proyecto.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    private final IngredienteRepository repo;

    public IngredienteService(IngredienteRepository repo) {
        this.repo = repo;
    }

    // Validaciones de negocio
    private void validate(Ingrediente i) {
        if (i.getNombre() == null || i.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del ingrediente no puede estar vacío");
        }
    }

    private void ensureNombreUniqueOnCreate(String nombre) {
        if (repo.existsByNombreIgnoreCase(nombre)) {
            throw new IllegalArgumentException("Ya existe un ingrediente con ese nombre");
        }
    }

    private void ensureNombreUniqueOnUpdate(Long id, String nombre) {
        if (repo.existsByNombreIgnoreCaseAndIdNot(nombre, id)) {
            throw new IllegalArgumentException("Ya existe otro ingrediente con ese nombre");
        }
    }

    // CRUD
    public Ingrediente create(Ingrediente i) {
        validate(i);
        ensureNombreUniqueOnCreate(i.getNombre());
        return repo.save(i);
    }

    public List<Ingrediente> findAll() {
        return repo.findAll();
    }

    public Ingrediente findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con id: " + id));
    }

    public Ingrediente findByNombre(String nombre) {
        return repo.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Ingrediente no encontrado con nombre: " + nombre));
    }

    public Ingrediente update(Long id, Ingrediente data) {
        Ingrediente current = findById(id);
        validate(data);
        ensureNombreUniqueOnUpdate(id, data.getNombre());
        current.setNombre(data.getNombre());
        return repo.save(current);
    }

    public void delete(Long id) {
        Ingrediente current = findById(id);
        repo.delete(current);
    }
}
