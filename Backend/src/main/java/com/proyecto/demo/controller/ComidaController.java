package com.proyecto.demo.controller;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.service.ComidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comidas")
@CrossOrigin(origins = "*") // permite peticiones desde el front
public class ComidaController {

    @Autowired
    private ComidaService comidaService;


    @PostMapping
    public ResponseEntity<Comida> crearComida(@RequestBody Comida comida,
                                              @RequestParam Long recetaId) {
        Comida nuevaComida = comidaService.crearComida(comida, recetaId);
        return ResponseEntity.ok(nuevaComida);
    }

 
    @GetMapping
    public ResponseEntity<List<Comida>> obtenerTodas() {
        List<Comida> comidas = comidaService.obtenerTodas();
        return ResponseEntity.ok(comidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comida> obtenerPorId(@PathVariable Long id) {
        Optional<Comida> comida = comidaService.obtenerPorId(id);
        return comida.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.notFound().build());
    }

 
    @PutMapping("/{id}")
    public ResponseEntity<Comida> actualizarComida(@PathVariable Long id,
                                                   @RequestBody Comida comidaActualizada) {
        Comida comida = comidaService.actualizarComida(id, comidaActualizada);
        return ResponseEntity.ok(comida);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComida(@PathVariable Long id) {
        comidaService.eliminarComida(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/por-receta/{recetaId}")
    public ResponseEntity<List<Comida>> obtenerPorReceta(@PathVariable Long recetaId) {
        List<Comida> comidas = comidaService.obtenerPorReceta(recetaId);
        return ResponseEntity.ok(comidas);
    }
}
