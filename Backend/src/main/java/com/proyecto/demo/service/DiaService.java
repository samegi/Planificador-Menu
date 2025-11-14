package com.proyecto.demo.service;

import com.proyecto.demo.model.Dia;
import com.proyecto.demo.model.Comida;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.DiaRepository;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.repository.RecetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class DiaService {

    private final DiaRepository diaRepository;
    private final ComidaRepository comidaRepository;
    private final RecetaRepository recetaRepository;

    public DiaService(DiaRepository diaRepository,
                      ComidaRepository comidaRepository,
                      RecetaRepository recetaRepository) {
        this.diaRepository = diaRepository;
        this.comidaRepository = comidaRepository;
        this.recetaRepository = recetaRepository;
    }

    // CRUD básico de Día
    public Dia crearDia(Dia dia) {
        return diaRepository.save(dia);
    }

    public List<Dia> listarDias() {
        return diaRepository.findAll();
    }

    public Dia obtenerDia(Long id) {
        return diaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Día no encontrado con ID: " + id));
    }

    public Dia actualizarDia(Long id, Dia dia) {
        Dia existente = obtenerDia(id);
        if (dia.getFecha() != null) { // no sobrescribir con null
            existente.setFecha(dia.getFecha());
        }
        return diaRepository.save(existente);
    }

    public void eliminarDia(Long id) {
        if (!diaRepository.existsById(id)) {
            throw new EntityNotFoundException("Día no encontrado con ID: " + id);
        }
        diaRepository.deleteById(id);
    }

    // -------- Agregar una comida a un día ----------
    public Comida agregarComidaADia(Long diaId, Long recetaId, LocalTime hora) {
        Dia dia = obtenerDia(diaId);
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada con ID: " + recetaId));

        Comida comida = new Comida();
        comida.setHora(hora);
        comida.setReceta(receta);
        comida.setDia(dia);

        // Mantener ambos lados de la relación en memoria
        dia.getComidas().add(comida);

        return comidaRepository.save(comida);
    }

    public List<Comida> listarComidasDeDia(Long diaId) {
        Dia dia = obtenerDia(diaId);
        return dia.getComidas();
    }
}
