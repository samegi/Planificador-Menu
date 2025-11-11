package com.proyecto.demo.service;

import com.proyecto.demo.datos.*;
import com.proyecto.demo.model.Dia;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.repository.DiaRepository;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import com.proyecto.demo.repository.IngredienteRepository;
import com.proyecto.demo.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {

    private final IngredienteRepository ingredienteRepository;
    private final RecetaRepository recetaRepository;
    private final ComidaRepository comidaRepository;
    private final DiaRepository diaRepository;
    private final IngredienteRecetaRepository ingredienteRecetaRepository;

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;

    public EstadisticasService(IngredienteRepository ingredienteRepository,
                               RecetaRepository recetaRepository,
                               ComidaRepository comidaRepository,
                               DiaRepository diaRepository,
                               IngredienteRecetaRepository ingredienteRecetaRepository) {
        this.ingredienteRepository = ingredienteRepository;
        this.recetaRepository = recetaRepository;
        this.comidaRepository = comidaRepository;
        this.diaRepository = diaRepository;
        this.ingredienteRecetaRepository = ingredienteRecetaRepository;
    }

    /** Resumen general agrupado en secciones para el front */
    public EstadisticasResumen resumen(int topN) {
        var totales = new EstadisticasResumen.Totales(
                ingredienteRepository.count(),
                recetaRepository.count(),
                comidaRepository.count()
        );

        var distribuciones = new EstadisticasResumen.Distribuciones(recetasPorMacronutriente());

        var tops = new EstadisticasResumen.Tops(
                ingredientesMasVersatiles(topN),
                recetasConMasIngredientes(topN),
                recetasMasConsumidas(topN)
        );

        var series = new EstadisticasResumen.Series(
                comidasPorDiaSerie()
        );

        return new EstadisticasResumen(totales, distribuciones, tops, series);
    }

    /** { "proteina": 3, "carbohidrato": 4, "grasa": 1, "sin_clasificar": 0 } */
    public Map<String, Long> recetasPorMacronutriente() {
        return recetaRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        r -> Optional.ofNullable(r.getMacronutriente())
                                     .map(Enum::name).orElse("SIN_CLASIFICAR")
                                     .toLowerCase(Locale.ROOT),
                        Collectors.counting()
                ));
    }

    /** Ingredientes más VERSÁTILES = aparecen en más recetas (tabla ingrediente_receta). */
    public List<ItemConteo> ingredientesMasVersatiles(int n) {
        Map<String, Long> conteo = ingredienteRecetaRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        ir -> Optional.ofNullable(ir.getIngrediente())
                                      .map(i -> i.getNombre())
                                      .orElse("desconocido"),
                        Collectors.counting()
                ));

        return conteo.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(Math.max(n, 0))
                .map(e -> new ItemConteo(e.getKey(), e.getValue()))
                .toList();
    }

    /** Top N recetas con mayor número de ingredientes distintos (tabla ingrediente_receta). */
    public List<ItemConteo> recetasConMasIngredientes(int n) {
        var porReceta = ingredienteRecetaRepository.findAll().stream()
                .collect(Collectors.groupingBy(ir -> ir.getReceta().getId(), Collectors.counting()));

        var nombres = recetaRepository.findAll().stream()
                .collect(Collectors.toMap(Receta::getId, Receta::getNombre));

        return porReceta.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(Math.max(n, 0))
                .map(e -> new ItemConteo(nombres.getOrDefault(e.getKey(), "receta_" + e.getKey()), e.getValue()))
                .toList();
    }

    /** Top N recetas más CONSUMIDAS = receta que más se repite en la tabla 'comida'. */
    public List<ItemConteo> recetasMasConsumidas(int n) {
        // Contar cuántas comidas hay por receta (usando el nombre de la receta como clave visible)
        Map<String, Long> conteo = comidaRepository.findAll().stream()
                .map(c -> Optional.ofNullable(c.getReceta()).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(
                        r -> Optional.ofNullable(r.getNombre()).orElse("receta_sin_nombre"),
                        Collectors.counting()
                ));

        return conteo.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(Math.max(n, 0))
                .map(e -> new ItemConteo(e.getKey(), e.getValue()))
                .toList();
    }

    /** Serie ordenada por fecha: [{fecha:"2025-11-10", cantidad:2}, ...] */
    public List<DatoFechaCantidad> comidasPorDiaSerie() {
        return diaRepository.findAll().stream()
                .sorted(Comparator.comparing(Dia::getFecha))
                .map(d -> new DatoFechaCantidad(
                        d.getFecha().format(ISO),
                        Optional.ofNullable(d.getComidas()).map(List::size).map(Long::valueOf).orElse(0L)
                ))
                .toList();
    }
}
