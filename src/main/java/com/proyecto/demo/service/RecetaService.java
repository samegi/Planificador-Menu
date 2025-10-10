package com.proyecto.demo.service;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.NivelPicante;
import com.proyecto.demo.repository.RecetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    private final RecetaRepository repo;

    public RecetaService(RecetaRepository repo) {
        this.repo = repo;
    }

    // ✅ Validaciones de negocio
    private void validate(Receta r) {
        if (r.getNombre() == null || r.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la receta no puede estar vacío.");
        }

        if (r.getNivelPicante() == null) {
            throw new IllegalArgumentException(
                "Debe indicar el nivel de picante (NULO, BAJO, MEDIO o ALTO)."
            );
        }

        // (Opcional) Validar que el valor de nivelPicante sea uno de los definidos
        boolean valido = false;
        for (NivelPicante nivel : NivelPicante.values()) {
            if (nivel == r.getNivelPicante()) {
                valido = true;
                break;
            }
        }
        if (!valido) {
            throw new IllegalArgumentException("Nivel de picante no válido.");
        }
    }

    public List<Receta> findAll() {
        return repo.findAll();
    }

    public Receta findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + id));
    }

    public Receta create(Receta r) {
        validate(r);

        if (repo.existsByNombreIgnoreCase(r.getNombre())) {
            throw new IllegalArgumentException("Ya existe una receta con ese nombre.");
        }

        return repo.save(r);
    }

    public Receta update(Long id, Receta data) {
        Receta current = findById(id);
        validate(data);

        current.setNombre(data.getNombre());
        current.setDescripcion(data.getDescripcion());
        current.setNivelPicante(data.getNivelPicante());

        return repo.save(current);
    }

    public void delete(Long id) {
        repo.delete(findById(id));
    }
}
