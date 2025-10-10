package com.proyecto.demo.controller;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.service.RecetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    // Crear receta
    @PostMapping
    public ResponseEntity<Receta> crearReceta(@RequestBody Receta receta) {
        Receta nueva = recetaService.crearReceta(receta);
        return ResponseEntity.ok(nueva);
    }

    // Listar recetas
    @GetMapping
    public ResponseEntity<List<Receta>> listarRecetas() {
        return ResponseEntity.ok(recetaService.listarRecetas());
    }

    // Obtener receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerReceta(@PathVariable Long id) {
        return ResponseEntity.ok(recetaService.obtenerReceta(id));
    }

    // Actualizar receta
    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable Long id, @RequestBody Receta receta) {
        return ResponseEntity.ok(recetaService.actualizarReceta(id, receta));
    }

    // Eliminar receta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}
