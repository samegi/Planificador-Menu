package com.proyecto.demo.Unit;

import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.repository.IngredienteRepository;
import com.proyecto.demo.service.IngredienteService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredienteServiceTest {

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private IngredienteService ingredienteService;

    private Ingrediente ingrediente1;
    private Ingrediente ingrediente2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ingrediente1 = new Ingrediente();
        ingrediente1.setId(1L);
        ingrediente1.setNombre("Tomate");

        ingrediente2 = new Ingrediente();
        ingrediente2.setId(2L);
        ingrediente2.setNombre("Cebolla");
    }

    @Test
    void crearIngrediente_deberiaGuardarNuevoIngrediente() {
        when(ingredienteRepository.existsByNombreIgnoreCase("Tomate")).thenReturn(false);
        when(ingredienteRepository.save(ingrediente1)).thenReturn(ingrediente1);

        Ingrediente resultado = ingredienteService.crearIngrediente(ingrediente1);

        assertNotNull(resultado);
        assertEquals("Tomate", resultado.getNombre());
        verify(ingredienteRepository, times(1)).save(ingrediente1);
    }

    @Test
    void crearIngrediente_deberiaLanzarExcepcionSiDuplicado() {
        when(ingredienteRepository.existsByNombreIgnoreCase("Tomate")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ingredienteService.crearIngrediente(ingrediente1));

        assertEquals("Ya existe un ingrediente con el nombre: Tomate", exception.getMessage());
        verify(ingredienteRepository, never()).save(any());
    }

    @Test
    void listarIngredientes_deberiaRetornarLista() {
        when(ingredienteRepository.findAll()).thenReturn(Arrays.asList(ingrediente1, ingrediente2));

        List<Ingrediente> lista = ingredienteService.listarIngredientes();

        assertEquals(2, lista.size());
        assertEquals("Tomate", lista.get(0).getNombre());
        verify(ingredienteRepository, times(1)).findAll();
    }

    @Test
    void obtenerIngredientePorId_deberiaRetornarIngrediente() {
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(ingrediente1));

        Ingrediente resultado = ingredienteService.obtenerIngredientePorId(1L);

        assertNotNull(resultado);
        assertEquals("Tomate", resultado.getNombre());
        verify(ingredienteRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerIngredientePorId_deberiaLanzarExcepcionSiNoExiste() {
        when(ingredienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> ingredienteService.obtenerIngredientePorId(99L));

        verify(ingredienteRepository, times(1)).findById(99L);
    }

    @Test
    void actualizarIngrediente_deberiaGuardarCambios() {
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(ingrediente1));
        when(ingredienteRepository.save(any(Ingrediente.class))).thenAnswer(inv -> inv.getArgument(0));

        Ingrediente actualizado = new Ingrediente();
        actualizado.setNombre("Tomate Cherry");

        Ingrediente resultado = ingredienteService.actualizarIngrediente(1L, actualizado);

        assertEquals("Tomate Cherry", resultado.getNombre());
        verify(ingredienteRepository, times(1)).save(any(Ingrediente.class));
    }

    @Test
    void eliminarIngrediente_deberiaEliminarPorId() {
        when(ingredienteRepository.findById(1L)).thenReturn(Optional.of(ingrediente1));

        ingredienteService.eliminarIngrediente(1L);

        verify(ingredienteRepository, times(1)).delete(ingrediente1);
    }

    @Test
    void eliminarIngrediente_deberiaLanzarExcepcionSiNoExiste() {
        when(ingredienteRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> ingredienteService.eliminarIngrediente(10L));

        verify(ingredienteRepository, never()).delete(any());
    }
}
