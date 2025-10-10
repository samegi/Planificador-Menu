package com.proyecto.demo.controller;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.service.RecetaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService service;

    public RecetaController(RecetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Receta> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Receta getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Receta create(@RequestBody Receta receta) {
        return service.create(receta);
    }

    @PutMapping("/{id}")
    public Receta update(@PathVariable Long id, @RequestBody Receta receta) {
        return service.update(id, receta);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

