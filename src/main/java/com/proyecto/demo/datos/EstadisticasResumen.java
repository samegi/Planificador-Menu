package com.proyecto.demo.datos;

import java.util.List;
import java.util.Map;

/** Resumen agrupado por secciones para facilitar el front */
public record EstadisticasResumen(
        Totales totales,
        Distribuciones distribuciones,
        Tops tops,
        Series series
) {
    /** Totales principales */
    public record Totales(long ingredientes, long recetas, long comidas) {}

    /** Distribuciones (p.ej. por macronutriente) */
    public record Distribuciones(Map<String, Long> recetasPorMacronutriente) {}

    /** Tops: listas ordenadas descendente por cantidad */
    public record Tops(
            List<ItemConteo> ingredientesMasVersatiles,   
            List<ItemConteo> recetasConMasIngredientes,   
            List<ItemConteo> recetasMasConsumidas         
    ) {}

    /** Series temporales y similares */
    public record Series(List<DatoFechaCantidad> comidasPorDia) {}
}
