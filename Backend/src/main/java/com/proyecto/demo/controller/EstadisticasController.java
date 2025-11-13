package com.proyecto.demo.controller;

import com.proyecto.demo.repository.RecetaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/estadisticas")
@CrossOrigin(origins = "http://localhost:5173") // permitir tu frontend
public class EstadisticasController {

    private final RecetaRepository recetaRepository;

    public EstadisticasController(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @GetMapping("/recetas/macronutrientes")
    public Map<String, Long> contarRecetasPorMacronutriente() {
        Map<String, Long> resultado = new HashMap<>();

        recetaRepository.findAll().forEach(receta -> {
            String macro = receta.getMacronutriente().name();
            resultado.put(macro, resultado.getOrDefault(macro, 0L) + 1);
        });

        return resultado;
    }
}
