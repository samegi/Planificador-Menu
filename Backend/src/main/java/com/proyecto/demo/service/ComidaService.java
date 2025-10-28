package com.proyecto.demo.service;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ComidaService {

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    // Crear una nueva comida
    public Comida crearComida(Comida comida, Long recetaId) {
        Optional<Receta> receta = recetaRepository.findById(recetaId);
        if (receta.isPresent()) {
            comida.setReceta(receta.get());
            return comidaRepository.save(comida);
        } else {
            throw new IllegalArgumentException("La receta con ID " + recetaId + " no existe.");
        }
    }

    // Obtener todas las comidas
    public List<Comida> obtenerTodas() {
        return comidaRepository.findAll();
    }

    // Obtener una comida por su ID
    public Optional<Comida> obtenerPorId(Long id) {
        return comidaRepository.findById(id);
    }

    // Actualizar una comida existente
    public Comida actualizarComida(Long id, Comida comidaActualizada) {
        return comidaRepository.findById(id).map(comida -> {
            comida.setHora(comidaActualizada.getHora());
            comida.setReceta(comidaActualizada.getReceta());
            return comidaRepository.save(comida);
        }).orElseThrow(() -> new RuntimeException("Comida no encontrada con id " + id));
    }

    // Eliminar una comida
    public void eliminarComida(Long id) {
        if (comidaRepository.existsById(id)) {
            comidaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe una comida con el ID proporcionado");
        }
    }

    // Obtener comidas por receta
    public List<Comida> obtenerPorReceta(Long recetaId) {
        return comidaRepository.findByRecetaId(recetaId);
    }
}
