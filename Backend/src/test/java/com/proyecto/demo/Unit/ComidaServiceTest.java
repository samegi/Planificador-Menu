package com.proyecto.demo.Unit;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.service.ComidaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComidaServiceTest {

    @Mock
    private ComidaRepository comidaRepository;

    @Mock
    private RecetaRepository recetaRepository;

    @InjectMocks
    private ComidaService comidaService;

    private Comida comida;
    private Receta receta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Arroz con pollo");

        comida = new Comida();
        comida.setId(1L);
        comida.setHora(LocalTime.of(12, 30));
        comida.setReceta(receta);
    }

    @Test
    void crearComida_DeberiaGuardarComidaCuandoRecetaExiste() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(comidaRepository.save(any(Comida.class))).thenReturn(comida);

        Comida resultado = comidaService.crearComida(comida, 1L);

        assertNotNull(resultado);
        assertEquals(receta, resultado.getReceta());
        verify(comidaRepository, times(1)).save(any(Comida.class));
    }

    @Test
    void crearComida_DeberiaLanzarExcepcionCuandoRecetaNoExiste() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            comidaService.crearComida(comida, 1L);
        });

        assertEquals("La receta con ID 1 no existe.", exception.getMessage());
        verify(comidaRepository, never()).save(any());
    }

    @Test
    void obtenerTodas_DeberiaRetornarListaDeComidas() {
        when(comidaRepository.findAll()).thenReturn(Arrays.asList(comida));

        List<Comida> resultado = comidaService.obtenerTodas();

        assertEquals(1, resultado.size());
        verify(comidaRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_DeberiaRetornarComidaCuandoExiste() {
        when(comidaRepository.findById(1L)).thenReturn(Optional.of(comida));

        Optional<Comida> resultado = comidaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(comida, resultado.get());
    }

    @Test
    void obtenerPorId_DeberiaRetornarVacioCuandoNoExiste() {
        when(comidaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Comida> resultado = comidaService.obtenerPorId(1L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void actualizarComida_DeberiaActualizarCamposYGuardar() {
        Comida actualizada = new Comida();
        actualizada.setHora(LocalTime.of(18, 0));
        actualizada.setReceta(receta);

        when(comidaRepository.findById(1L)).thenReturn(Optional.of(comida));
        when(comidaRepository.save(any(Comida.class))).thenReturn(actualizada);

        Comida resultado = comidaService.actualizarComida(1L, actualizada);

        assertEquals(LocalTime.of(18, 0), resultado.getHora());
        verify(comidaRepository, times(1)).save(any(Comida.class));
    }

    @Test
    void actualizarComida_DeberiaLanzarExcepcionSiNoExiste() {
        when(comidaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> comidaService.actualizarComida(1L, comida));
    }

    @Test
    void eliminarComida_DeberiaEliminarSiExiste() {
        when(comidaRepository.existsById(1L)).thenReturn(true);

        comidaService.eliminarComida(1L);

        verify(comidaRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarComida_DeberiaLanzarExcepcionSiNoExiste() {
        when(comidaRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> comidaService.eliminarComida(1L));
        verify(comidaRepository, never()).deleteById(any());
    }

    @Test
    void obtenerPorReceta_DeberiaRetornarListaDeComidas() {
        when(comidaRepository.findByRecetaId(1L)).thenReturn(List.of(comida));

        List<Comida> resultado = comidaService.obtenerPorReceta(1L);

        assertEquals(1, resultado.size());
        verify(comidaRepository, times(1)).findByRecetaId(1L);
    }
}
