package com.proyecto.demo.controller;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.service.RecetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.proyecto.demo.model.Macronutriente;

import java.util.List;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @PostMapping
   public ResponseEntity<Receta> crearReceta(
        @RequestParam String nombre,
        @RequestParam(required = false) String descripcion,
        @RequestParam Macronutriente macronutriente   // 👈 NUEVO
) {
    Receta r = new Receta();
    r.setNombre(nombre);
    r.setDescripcion(descripcion);
    r.setMacronutriente(macronutriente);             // 👈 CLAVE

    Receta nueva = recetaService.crearReceta(r);
    return ResponseEntity.ok(nueva);
}

    @GetMapping
    public ResponseEntity<List<Receta>> listarRecetas() {
        return ResponseEntity.ok(recetaService.listarRecetas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> obtenerReceta(@PathVariable Long id) {
        return ResponseEntity.ok(recetaService.obtenerReceta(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(
            @PathVariable Long id,
            @RequestParam String nombre,
            @RequestParam(required = false) String descripcion
    ) {
        Receta r = new Receta();
        r.setNombre(nombre);
        r.setDescripcion(descripcion);

        Receta actualizada = recetaService.actualizarReceta(id, r);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}
