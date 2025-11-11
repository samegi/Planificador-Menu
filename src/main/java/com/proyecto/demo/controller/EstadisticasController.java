package com.proyecto.demo.controller;

import com.proyecto.demo.datos.EstadisticasResumen;
import com.proyecto.demo.datos.ItemConteo;
import com.proyecto.demo.datos.DatoFechaCantidad;
import com.proyecto.demo.service.EstadisticasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final EstadisticasService service;

    public EstadisticasController(EstadisticasService service) {
        this.service = service;
    }

    /** Resumen completo (topN configurable, default 5) */
    @GetMapping("/resumen")
    public EstadisticasResumen resumen(@RequestParam(defaultValue = "5") int topN) {
        return service.resumen(topN);
    }

    /** Distribución de recetas por macronutriente */
    @GetMapping("/macros")
    public Map<String, Long> macros() {
        return service.recetasPorMacronutriente();
    }

    /** Top N ingredientes más usados */
    @GetMapping("/top-ingredientes")
    public List<ItemConteo> topIngredientes(@RequestParam(defaultValue = "5") int n) {
        return service.ingredientesMasUsados(n);
    }

    /** Top N recetas con más ingredientes */
    @GetMapping("/recetas-mas-ingredientes")
    public List<ItemConteo> recetasMasIngredientes(@RequestParam(defaultValue = "5") int n) {
        return service.recetasConMasIngredientes(n);
    }

    /** Comidas por día como serie temporal */
    @GetMapping("/comidas-por-dia")
    public List<DatoFechaCantidad> comidasPorDia() {
        return service.comidasPorDiaSerie();
    }
}
