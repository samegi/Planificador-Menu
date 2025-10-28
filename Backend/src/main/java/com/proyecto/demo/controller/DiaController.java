package com.proyecto.demo.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.model.Dia;
import com.proyecto.demo.service.DiaService;

@RestController
@RequestMapping("/api/dias")
@CrossOrigin(origins = "*")
public class DiaController {

    private final DiaService diaService;

    public DiaController(DiaService diaService) {
        this.diaService = diaService;
    }

    // ---- CRUD DIA ----
    @PostMapping
    public ResponseEntity<Dia> crearDia(@RequestBody Dia dia) {
        return ResponseEntity.ok(diaService.crearDia(dia));
    }

    @GetMapping
    public ResponseEntity<List<Dia>> listarDias() {
        return ResponseEntity.ok(diaService.listarDias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dia> obtenerDia(@PathVariable Long id) {
        return ResponseEntity.ok(diaService.obtenerDia(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dia> actualizarDia(@PathVariable Long id, @RequestBody Dia dia) {
        return ResponseEntity.ok(diaService.actualizarDia(id, dia));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDia(@PathVariable Long id) {
        diaService.eliminarDia(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Opcionales para gestionar comidas del día ----
    @PostMapping("/{diaId}/comidas")
    public ResponseEntity<Comida> agregarComidaADia(
            @PathVariable Long diaId,
            @RequestParam Long recetaId,
            @RequestParam String hora // formato "HH:mm"
    ) {
        LocalTime t = LocalTime.parse(hora);
        return ResponseEntity.ok(diaService.agregarComidaADia(diaId, recetaId, t));
    }

    @GetMapping("/{diaId}/comidas")
    public ResponseEntity<List<Comida>> listarComidasDeDia(@PathVariable Long diaId) {
        return ResponseEntity.ok(diaService.listarComidasDeDia(diaId));
    }
}
