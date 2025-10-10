package com.proyecto.demo.service;


import com.proyecto.demo.exception.ResourceNotFoundException;
import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.repository.IngredienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredienteServiceTest {

    @Mock
    private IngredienteRepository repo;

    @InjectMocks
    private IngredienteService service;

    private Ingrediente ingrediente;

    @BeforeEach
    void setUp() {
        ingrediente = new Ingrediente();
        ingrediente.setId(1L);
        ingrediente.setNombre("Sal");
    }

    @Test
    void create_IngredienteExitoso() {
        when(repo.existsByNombreIgnoreCase("Sal")).thenReturn(false);
        when(repo.save(ingrediente)).thenReturn(ingrediente);

        Ingrediente result = service.create(ingrediente);

        assertEquals("Sal", result.getNombre());
        verify(repo).save(ingrediente);
    }

    @Test
    void create_IngredienteNombreDuplicado() {
        when(repo.existsByNombreIgnoreCase("Sal")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.create(ingrediente));
        verify(repo, never()).save(any());
    }

    @Test
    void findById_IngredienteExistente() {
        when(repo.findById(1L)).thenReturn(Optional.of(ingrediente));

        Ingrediente result = service.findById(1L);
        assertEquals("Sal", result.getNombre());
    }

    @Test
    void findById_NoExistenteLanzaExcepcion() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void findAll_RetornaLista() {
        when(repo.findAll()).thenReturn(List.of(ingrediente));

        List<Ingrediente> result = service.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void update_IngredienteExistente() {
        Ingrediente datos = new Ingrediente();
        datos.setNombre("Azúcar");

        // El ingrediente actual existe
        when(repo.findById(1L)).thenReturn(Optional.of(ingrediente));
        // No hay otro ingrediente con el mismo nombre
        when(repo.existsByNombreIgnoreCaseAndIdNot("Azúcar", 1L)).thenReturn(false);
        // Simulamos el guardado
        when(repo.save(any(Ingrediente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ingrediente result = service.update(1L, datos);

        assertEquals("Azúcar", result.getNombre());
        verify(repo).save(any(Ingrediente.class));
        verify(repo).findById(1L);
        verify(repo).existsByNombreIgnoreCaseAndIdNot("Azúcar", 1L);
    }

    @Test
    void update_NombreDuplicadoLanzaExcepcion() {
        Ingrediente datos = new Ingrediente();
        datos.setNombre("Sal"); // Nombre duplicado

        when(repo.findById(1L)).thenReturn(Optional.of(ingrediente));
        // Simulamos que ya existe otro ingrediente con ese nombre y distinto ID
        when(repo.existsByNombreIgnoreCaseAndIdNot("Sal", 1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.update(1L, datos));

        verify(repo, never()).save(any());
        verify(repo).existsByNombreIgnoreCaseAndIdNot("Sal", 1L);
    }

    @Test
    void delete_IngredienteExistente() {
        when(repo.findById(1L)).thenReturn(Optional.of(ingrediente));
        doNothing().when(repo).delete(ingrediente);

        service.delete(1L);
        verify(repo).delete(ingrediente);
    }
}

