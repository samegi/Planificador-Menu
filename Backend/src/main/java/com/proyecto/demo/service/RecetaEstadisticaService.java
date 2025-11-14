package com.proyecto.demo.service;

import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.Macronutriente;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class RecetaEstadisticaService {

    private final RecetaRepository recetaRepository;

    public Map<String, Long> contarPorMacronutriente() {
        List<Receta> recetas = recetaRepository.findAll();

        Map<String, Long> conteo = new HashMap<>();

        for (Macronutriente m : Macronutriente.values()) {
            long cantidad = recetas.stream()
                    .filter(r -> r.getMacronutriente() == m)
                    .count();

            conteo.put(m.name(), cantidad);
        }

        return conteo;
    }
}
