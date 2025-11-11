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

    /** Resumen completo (top 3) */
    @GetMapping("/resumen")
    public EstadisticasResumen resumen(@RequestParam(defaultValue = "3") int topN) {
        return service.resumen(topN);
    }

    /** Distribución de recetas por macronutriente */
    @GetMapping("/macros")
    public Map<String, Long> macros() {
        return service.recetasPorMacronutriente();
    }

    /** Top 3 ingredientes más versátiles (aparecen en más recetas) */
    @GetMapping("/top-ingredientes-versatiles")
    public List<ItemConteo> topIngredientesVersatiles(@RequestParam(defaultValue = "3") int n) {
        return service.ingredientesMasVersatiles(n);
    }

    /** Alias por compatibilidad con lo previo (opcional) */
    @GetMapping("/top-ingredientes")
    public List<ItemConteo> topIngredientes(@RequestParam(defaultValue = "3") int n) {
        return service.ingredientesMasVersatiles(n);
    }

    /** Top 3 recetas con más ingredientes distintos */
    @GetMapping("/recetas-mas-ingredientes")
    public List<ItemConteo> recetasMasIngredientes(@RequestParam(defaultValue = "3") int n) {
        return service.recetasConMasIngredientes(n);
    }

    /** Top 3 recetas más consumidas (según tabla 'comida') */
    @GetMapping("/recetas-mas-consumidas")
    public List<ItemConteo> recetasMasConsumidas(@RequestParam(defaultValue = "3") int n) {
        return service.recetasMasConsumidas(n);
    }

    /** Comidas por día como serie temporal */
    @GetMapping("/comidas-por-dia")
    public List<DatoFechaCantidad> comidasPorDia() {
        return service.comidasPorDiaSerie();
    }
}
