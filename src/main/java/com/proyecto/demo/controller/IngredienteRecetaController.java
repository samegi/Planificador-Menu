package com.proyecto.demo.controller;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.service.IngredienteRecetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientesReceta")
public class IngredienteRecetaController {

    private final IngredienteRecetaService service;

    public IngredienteRecetaController(IngredienteRecetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<IngredienteReceta> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public IngredienteReceta getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public IngredienteReceta create(@RequestBody IngredienteReceta ir) {
        return service.create(ir);
    }

    @PutMapping("/{id}")
    public IngredienteReceta update(@PathVariable Long id, @RequestBody IngredienteReceta ir) {
        return service.update(id, ir);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

