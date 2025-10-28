package com.proyecto.demo.controller;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.service.IngredienteRecetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredientes-receta")
@CrossOrigin(origins = "*")
public class IngredienteRecetaController {

    private final IngredienteRecetaService ingredienteRecetaService;

    public IngredienteRecetaController(IngredienteRecetaService ingredienteRecetaService) {
        this.ingredienteRecetaService = ingredienteRecetaService;
    }

    // Crear nueva relación
    @PostMapping
    public ResponseEntity<IngredienteReceta> crearIngredienteReceta(
            @RequestParam Long recetaId,
            @RequestParam Long ingredienteId,
            @RequestParam float cantidad) {

        IngredienteReceta nueva = ingredienteRecetaService.crearIngredienteReceta(recetaId, ingredienteId, cantidad);
        return ResponseEntity.ok(nueva);
    }

    // Listar todas las relaciones
    @GetMapping
    public ResponseEntity<List<IngredienteReceta>> listarTodas() {
        return ResponseEntity.ok(ingredienteRecetaService.listarTodas());
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteReceta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingredienteRecetaService.obtenerPorId(id));
    }

    // Buscar por receta
    @GetMapping("/receta/{recetaId}")
    public ResponseEntity<List<IngredienteReceta>> buscarPorReceta(@PathVariable Long recetaId) {
        return ResponseEntity.ok(ingredienteRecetaService.buscarPorReceta(recetaId));
    }

    // Buscar por ingrediente
    @GetMapping("/ingrediente/{ingredienteId}")
    public ResponseEntity<List<IngredienteReceta>> buscarPorIngrediente(@PathVariable Long ingredienteId) {
        return ResponseEntity.ok(ingredienteRecetaService.buscarPorIngrediente(ingredienteId));
    }

    // Actualizar cantidad
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteReceta> actualizarCantidad(
            @PathVariable Long id,
            @RequestParam float cantidad) {
        return ResponseEntity.ok(ingredienteRecetaService.actualizarCantidad(id, cantidad));
    }

    // Eliminar relación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ingredienteRecetaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
