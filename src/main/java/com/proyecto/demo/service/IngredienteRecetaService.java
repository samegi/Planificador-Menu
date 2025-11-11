package com.proyecto.demo.service;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import com.proyecto.demo.repository.IngredienteRepository;
import com.proyecto.demo.repository.RecetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class IngredienteRecetaService {

    private final IngredienteRecetaRepository ingredienteRecetaRepository;
    private final RecetaRepository recetaRepository;
    private final IngredienteRepository ingredienteRepository;

    public IngredienteRecetaService(IngredienteRecetaRepository ingredienteRecetaRepository, RecetaRepository recetaRepository, IngredienteRepository ingredienteRepository) {
        this.ingredienteRecetaRepository = ingredienteRecetaRepository;
        this.recetaRepository = recetaRepository;
        this.ingredienteRepository = ingredienteRepository;
    }

    // Crear nueva relación con validación de duplicado
    public IngredienteReceta crearIngredienteReceta(Long idReceta, Long idIngrediente, float cantidad) {
        Receta receta = recetaRepository.findById(idReceta)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con ID: " + idReceta));

        Ingrediente ingrediente = ingredienteRepository.findById(idIngrediente)
                .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado con ID: " + idIngrediente));

        // Validar duplicado
        if (ingredienteRecetaRepository.existsByRecetaIdAndIngredienteId(idReceta, idIngrediente)) {
            throw new IllegalArgumentException(
                    "El ingrediente '" + ingrediente.getNombre() + "' ya está asociado a la receta '" + receta.getNombre() + "'."
            );
        }

        // Crear nueva relación
        IngredienteReceta relacion = new IngredienteReceta();
        relacion.setReceta(receta);
        relacion.setIngrediente(ingrediente);
        relacion.setCantidad(cantidad);

        return ingredienteRecetaRepository.save(relacion);
    }

    // --- Métodos restantes sin cambios ---
    public List<IngredienteReceta> listarTodas() {
        return ingredienteRecetaRepository.findAll();
    }

    public IngredienteReceta obtenerPorId(Long id) {
        return ingredienteRecetaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relación no encontrada con ID: " + id));
    }

    public List<IngredienteReceta> buscarPorReceta(Long recetaId) {
        return ingredienteRecetaRepository.findByRecetaId(recetaId);
    }

    public List<IngredienteReceta> buscarPorIngrediente(Long ingredienteId) {
        return ingredienteRecetaRepository.findByIngredienteId(ingredienteId);
    }

    public IngredienteReceta actualizarCantidad(Long id, float nuevaCantidad) {
        IngredienteReceta ir = obtenerPorId(id);
        ir.setCantidad(nuevaCantidad);
        return ingredienteRecetaRepository.save(ir);
    }

    public void eliminar(Long id) {
        IngredienteReceta ir = obtenerPorId(id);
        ingredienteRecetaRepository.delete(ir);
    }


 public void generarListaDeComprasPorRecetas() {
    //  Obtener todas las relaciones receta–ingrediente
    List<IngredienteReceta> relaciones = ingredienteRecetaRepository.findAll();

    //  Usar un Map para acumular cantidades por ingrediente
    Map<String, Double> listaCompras = new HashMap<>();

    for (IngredienteReceta relacion : relaciones) {
        String nombreIngrediente = relacion.getIngrediente().getNombre();
        double cantidad = relacion.getCantidad();

        // Sumar si el ingrediente ya existe
        listaCompras.put(nombreIngrediente,
                listaCompras.getOrDefault(nombreIngrediente, 0.0) + cantidad);
    }

    // Crear el archivo de texto
    String rutaArchivo = "lista_compras.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
        writer.write("=== LISTA DE COMPRAS POR RECETAS ===\n\n");

        for (Map.Entry<String, Double> entrada : listaCompras.entrySet()) {
            writer.write("- " + entrada.getKey() + ": " + entrada.getValue() + "\n");
        }

        writer.write("\nTotal ingredientes distintos: " + listaCompras.size());
        System.out.println(" Archivo generado correctamente en: " + rutaArchivo);

    } catch (IOException e) {
        System.err.println("Error al generar la lista de compras: " + e.getMessage());
    }
}
}
