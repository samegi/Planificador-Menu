package com.proyecto.demo.controller;

import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.service.IngredienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
@CrossOrigin(origins = "*")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    // Crear nuevo ingrediente
    @PostMapping
    public ResponseEntity<Ingrediente> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        Ingrediente nuevo = ingredienteService.crearIngrediente(ingrediente);
        return ResponseEntity.ok(nuevo);
    }

    // Listar todos los ingredientes
    @GetMapping
    public ResponseEntity<List<Ingrediente>> listarIngredientes() {
        return ResponseEntity.ok(ingredienteService.listarIngredientes());
    }

    // Obtener ingrediente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> obtenerIngrediente(@PathVariable Long id) {
        return ResponseEntity.ok(ingredienteService.obtenerIngredientePorId(id));
    }

    // Actualizar ingrediente
    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> actualizarIngrediente(
            @PathVariable Long id,
            @RequestBody Ingrediente ingrediente) {
        Ingrediente actualizado = ingredienteService.actualizarIngrediente(id, ingrediente);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar ingrediente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable Long id) {
        ingredienteService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }
}
