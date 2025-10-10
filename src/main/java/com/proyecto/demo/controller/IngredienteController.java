package com.proyecto.demo.web;

import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.service.IngredienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {

    private final IngredienteService service;

    public IngredienteController(IngredienteService service) {
        this.service = service;
    }

    // GET: listar todos
    @GetMapping
    public List<Ingrediente> getAll() {
        return service.findAll();
    }

    // GET: por id
    @GetMapping("/{id}")
    public Ingrediente getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // GET: por nombre ?nombre=Azucar
    @GetMapping("/search")
    public Ingrediente getByNombre(@RequestParam String nombre) {
        return service.findByNombre(nombre);
    }

    // POST: crear
    @PostMapping
    public ResponseEntity<Ingrediente> create(@RequestBody Ingrediente body) {
        Ingrediente created = service.create(body);
        return ResponseEntity.created(URI.create("/api/ingredientes/" + created.getId())).body(created);
    }

    // PUT: actualizar
    @PutMapping("/{id}")
    public Ingrediente update(@PathVariable Long id, @RequestBody Ingrediente body) {
        return service.update(id, body);
    }

    // DELETE: eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
