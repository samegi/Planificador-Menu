package com.proyecto.demo.service;

import com.proyecto.demo.model.NivelPicante;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.RecetaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecetaServiceTest {

    @Mock
    private RecetaRepository repo;

    @InjectMocks
    private RecetaService service;

    private Receta receta;

    @BeforeEach
    void setUp() {
        receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Guacamole");
        receta.setDescripcion("Salsa tradicional");
        receta.setNivelPicante(NivelPicante.MEDIO);
    }

    // ---------- CREATE ----------
    @Test
    void create_RecetaExitosa() {
        when(repo.existsByNombreIgnoreCase("Guacamole")).thenReturn(false);
        when(repo.save(receta)).thenReturn(receta);

        Receta result = service.create(receta);

        assertEquals("Guacamole", result.getNombre());
        verify(repo).existsByNombreIgnoreCase("Guacamole");
        verify(repo).save(receta);
    }

    @Test
    void create_RecetaDuplicadaLanzaExcepcion() {
        when(repo.existsByNombreIgnoreCase("Guacamole")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.create(receta));
        verify(repo, never()).save(any());
    }

    // ---------- READ ----------
    @Test
    void findById_RecetaExistente() {
        when(repo.findById(1L)).thenReturn(Optional.of(receta));

        Receta result = service.findById(1L);

        assertEquals("Guacamole", result.getNombre());
        assertEquals(NivelPicante.MEDIO, result.getNivelPicante());
        verify(repo).findById(1L);
    }

    @Test
    void findById_NoExistenteLanzaExcepcion() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.findById(1L));
        verify(repo).findById(1L);
    }

    @Test
    void findAll_RetornaLista() {
        when(repo.findAll()).thenReturn(List.of(receta));

        List<Receta> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Guacamole", result.get(0).getNombre());
        verify(repo).findAll();
    }

    // ---------- UPDATE ----------
    @Test
    void update_RecetaExistente() {
        Receta datos = new Receta();
        datos.setNombre("Salsa Verde");
        datos.setDescripcion("Hecha con chiles verdes");
        datos.setNivelPicante(NivelPicante.ALTO);

        when(repo.findById(1L)).thenReturn(Optional.of(receta));
        // OJO: el update de tu servicio NO llama a existsBy... para duplicados
        when(repo.save(any(Receta.class))).thenAnswer(inv -> inv.getArgument(0));

        Receta result = service.update(1L, datos);

        assertEquals("Salsa Verde", result.getNombre());
        assertEquals("Hecha con chiles verdes", result.getDescripcion());
        assertEquals(NivelPicante.ALTO, result.getNivelPicante());
        verify(repo).findById(1L);
        verify(repo).save(any(Receta.class));
    }

    @Test
    void update_NombreVacioLanzaExcepcion() {
        Receta datos = new Receta();
        datos.setNombre(""); // inválido según validate
        datos.setDescripcion("desc");
        datos.setNivelPicante(NivelPicante.BAJO);

        when(repo.findById(1L)).thenReturn(Optional.of(receta));

        assertThrows(IllegalArgumentException.class, () -> service.update(1L, datos));
        verify(repo, never()).save(any());
    }

    @Test
    void update_NivelPicanteNuloLanzaExcepcion() {
        Receta datos = new Receta();
        datos.setNombre("Nueva");
        datos.setDescripcion("desc");
        datos.setNivelPicante(null); // inválido según validate

        when(repo.findById(1L)).thenReturn(Optional.of(receta));

        assertThrows(IllegalArgumentException.class, () -> service.update(1L, datos));
        verify(repo, never()).save(any());
    }

    // ---------- DELETE ----------
    @Test
    void delete_RecetaExistente() {
        when(repo.findById(1L)).thenReturn(Optional.of(receta));
        doNothing().when(repo).delete(receta);

        service.delete(1L);

        verify(repo).delete(receta);
    }

    @Test
    void delete_NoExistenteLanzaExcepcion() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.delete(1L));
        verify(repo, never()).delete(any());
    }
}
