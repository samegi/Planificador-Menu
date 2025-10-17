package com.proyecto.demo.Unit;

import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import com.proyecto.demo.repository.IngredienteRepository;
import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.service.IngredienteRecetaService;

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

class IngredienteRecetaServiceTest {

    @Mock
    private IngredienteRecetaRepository ingredienteRecetaRepository;

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private IngredienteRepository ingredienteRepository;

    @InjectMocks
    private IngredienteRecetaService ingredienteRecetaService;

    private Receta receta;
    private Ingrediente ingrediente;
    private IngredienteReceta relacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Arroz con Pollo");

        ingrediente = new Ingrediente();
        ingrediente.setId(2L);
        ingrediente.setNombre("Tomate");

        relacion = new IngredienteReceta();
        relacion.setId(10L);
        relacion.setReceta(receta);
        relacion.setIngrediente(ingrediente);
        relacion.setCantidad(200f);
    }

    @Test
    void crearIngredienteReceta_deberiaGuardarRelacion() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(ingredienteRepository.findById(2L)).thenReturn(Optional.of(ingrediente));
        when(ingredienteRecetaRepository.existsByRecetaIdAndIngredienteId(1L, 2L)).thenReturn(false);
        when(ingredienteRecetaRepository.save(any(IngredienteReceta.class))).thenReturn(relacion);

        IngredienteReceta resultado = ingredienteRecetaService.crearIngredienteReceta(1L, 2L, 200f);

        assertNotNull(resultado);
        assertEquals(200f, resultado.getCantidad());
        assertEquals("Tomate", resultado.getIngrediente().getNombre());
        assertEquals("Arroz con Pollo", resultado.getReceta().getNombre());
        verify(ingredienteRecetaRepository, times(1)).save(any(IngredienteReceta.class));
    }

    @Test
    void crearIngredienteReceta_deberiaLanzarExcepcionSiDuplicado() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(ingredienteRepository.findById(2L)).thenReturn(Optional.of(ingrediente));
        when(ingredienteRecetaRepository.existsByRecetaIdAndIngredienteId(1L, 2L)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                ingredienteRecetaService.crearIngredienteReceta(1L, 2L, 100f));

        assertTrue(ex.getMessage().contains("ya está asociado"));
        verify(ingredienteRecetaRepository, never()).save(any());
    }

    @Test
    void crearIngredienteReceta_deberiaLanzarExcepcionSiRecetaNoExiste() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> ingredienteRecetaService.crearIngredienteReceta(1L, 2L, 150f));

        verify(ingredienteRecetaRepository, never()).save(any());
    }

    @Test
    void crearIngredienteReceta_deberiaLanzarExcepcionSiIngredienteNoExiste() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(ingredienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> ingredienteRecetaService.crearIngredienteReceta(1L, 2L, 150f));

        verify(ingredienteRecetaRepository, never()).save(any());
    }

    @Test
    void listarTodas_deberiaRetornarLista() {
        when(ingredienteRecetaRepository.findAll()).thenReturn(Arrays.asList(relacion));

        List<IngredienteReceta> lista = ingredienteRecetaService.listarTodas();

        assertEquals(1, lista.size());
        assertEquals("Tomate", lista.get(0).getIngrediente().getNombre());
        verify(ingredienteRecetaRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_deberiaRetornarRelacion() {
        when(ingredienteRecetaRepository.findById(10L)).thenReturn(Optional.of(relacion));

        IngredienteReceta resultado = ingredienteRecetaService.obtenerPorId(10L);

        assertNotNull(resultado);
        assertEquals(200f, resultado.getCantidad());
        verify(ingredienteRecetaRepository, times(1)).findById(10L);
    }

    @Test
    void obtenerPorId_deberiaLanzarExcepcionSiNoExiste() {
        when(ingredienteRecetaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> ingredienteRecetaService.obtenerPorId(99L));

        verify(ingredienteRecetaRepository, times(1)).findById(99L);
    }

    @Test
    void buscarPorReceta_deberiaRetornarRelaciones() {
        when(ingredienteRecetaRepository.findByRecetaId(1L)).thenReturn(Arrays.asList(relacion));

        List<IngredienteReceta> resultado = ingredienteRecetaService.buscarPorReceta(1L);

        assertEquals(1, resultado.size());
        verify(ingredienteRecetaRepository, times(1)).findByRecetaId(1L);
    }

    @Test
    void buscarPorIngrediente_deberiaRetornarRelaciones() {
        when(ingredienteRecetaRepository.findByIngredienteId(2L)).thenReturn(Arrays.asList(relacion));

        List<IngredienteReceta> resultado = ingredienteRecetaService.buscarPorIngrediente(2L);

        assertEquals(1, resultado.size());
        verify(ingredienteRecetaRepository, times(1)).findByIngredienteId(2L);
    }

    @Test
    void actualizarCantidad_deberiaModificarYGuardar() {
        when(ingredienteRecetaRepository.findById(10L)).thenReturn(Optional.of(relacion));
        when(ingredienteRecetaRepository.save(any(IngredienteReceta.class))).thenAnswer(inv -> inv.getArgument(0));

        IngredienteReceta resultado = ingredienteRecetaService.actualizarCantidad(10L, 300f);

        assertEquals(300f, resultado.getCantidad());
        verify(ingredienteRecetaRepository, times(1)).save(any(IngredienteReceta.class));
    }

    @Test
    void eliminar_deberiaEliminarRelacion() {
        when(ingredienteRecetaRepository.findById(10L)).thenReturn(Optional.of(relacion));

        ingredienteRecetaService.eliminar(10L);

        verify(ingredienteRecetaRepository, times(1)).delete(relacion);
    }

    @Test
    void eliminar_deberiaLanzarExcepcionSiNoExiste() {
        when(ingredienteRecetaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> ingredienteRecetaService.eliminar(99L));

        verify(ingredienteRecetaRepository, never()).delete(any());
    }
}

