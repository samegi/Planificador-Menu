package com.proyecto.demo.service;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteRecetaService {

    private final IngredienteRecetaRepository repo;

    public IngredienteRecetaService(IngredienteRecetaRepository repo) {
        this.repo = repo;
    }

    public List<IngredienteReceta> findAll() {
        return repo.findAll();
    }

    public IngredienteReceta findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("IngredienteReceta no encontrada"));
    }

    public IngredienteReceta create(IngredienteReceta ir) {
        return repo.save(ir);
    }

    public IngredienteReceta update(Long id, IngredienteReceta data) {
        IngredienteReceta current = findById(id);
        current.setCantidad(data.getCantidad());
        current.setIngrediente(data.getIngrediente());
        current.setReceta(data.getReceta());
        return repo.save(current);
    }

    public void delete(Long id) {
        repo.delete(findById(id));
    }
}
