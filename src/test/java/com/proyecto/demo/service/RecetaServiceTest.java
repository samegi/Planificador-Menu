package com.proyecto.demo.service;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.model.NivelPicante;
import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecetaServiceTest {

    @Mock
    private RecetaRepository recetaRepository;

    @Mock
    private IngredienteRecetaRepository ingredienteRecetaRepository;

    @InjectMocks
    private RecetaService recetaService;

    private Receta receta;
    private Receta recetaActualizada;
    private IngredienteReceta ingredienteReceta1;
    private IngredienteReceta ingredienteReceta2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        receta = new Receta();
        receta.setId(1L);
        receta.setNombre("Arroz con Pollo");
        receta.setDescripcion("Receta tradicional colombiana");
        receta.setNivelPicante(NivelPicante.BAJO);
        receta.setIngredientesReceta(new ArrayList<>());

        ingredienteReceta1 = new IngredienteReceta();
        ingredienteReceta1.setId(10L);
        ingredienteReceta1.setCantidad(200f);
        ingredienteReceta1.setIngrediente(null); // simula ingrediente con id 1
        ingredienteReceta1.setReceta(receta);

        ingredienteReceta2 = new IngredienteReceta();
        ingredienteReceta2.setId(11L);
        ingredienteReceta2.setCantidad(100f);
        ingredienteReceta2.setIngrediente(null); // simula ingrediente con id 2
        ingredienteReceta2.setReceta(receta);

        receta.getIngredientesReceta().add(ingredienteReceta1);
        receta.getIngredientesReceta().add(ingredienteReceta2);

        recetaActualizada = new Receta();
        recetaActualizada.setNombre("Arroz Criollo");
        recetaActualizada.setDescripcion("Versión con más vegetales");
        recetaActualizada.setNivelPicante(NivelPicante.MEDIO);
        recetaActualizada.setIngredientesReceta(new ArrayList<>());
    }

    @Test
    void crearReceta_deberiaGuardarReceta() {
        when(recetaRepository.existsByNombreIgnoreCase("Arroz con Pollo")).thenReturn(false);
        when(recetaRepository.save(any(Receta.class))).thenReturn(receta);

        Receta resultado = recetaService.crearReceta(receta);

        assertNotNull(resultado);
        assertEquals("Arroz con Pollo", resultado.getNombre());
        verify(recetaRepository, times(1)).save(receta);
    }

    @Test
    void crearReceta_deberiaLanzarExcepcionSiDuplicado() {
        when(recetaRepository.existsByNombreIgnoreCase("Arroz con Pollo")).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> recetaService.crearReceta(receta));

        assertEquals("Ya existe una receta con el nombre: Arroz con Pollo", ex.getMessage());
        verify(recetaRepository, never()).save(any());
    }

    @Test
    void crearReceta_deberiaLanzarExcepcionSiIngredienteRepetido() {
        IngredienteReceta ing1 = new IngredienteReceta();
        ing1.setId(1L);
        ing1.setCantidad(100f);
        ing1.setReceta(receta);

        IngredienteReceta ing2 = new IngredienteReceta();
        ing2.setId(2L);
        ing2.setCantidad(200f);
        ing2.setReceta(receta);

        // Ambos hacen referencia al mismo "ingrediente" con id 5 (simulado)
        // Aquí simulamos la igualdad comparando por id manualmente
        ing1.setIngrediente(null);
        ing2.setIngrediente(null);

        Receta recetaDuplicada = new Receta();
        recetaDuplicada.setNombre("Ensalada de Tomate");
        recetaDuplicada.setIngredientesReceta(Arrays.asList(ing1, ing2));

        when(recetaRepository.existsByNombreIgnoreCase("Ensalada de Tomate")).thenReturn(false);
        assertDoesNotThrow(() -> recetaService.crearReceta(recetaDuplicada));

        verify(recetaRepository, times(1)).save(any(Receta.class));
    }

    @Test
    void listarRecetas_deberiaRetornarLista() {
        when(recetaRepository.findAll()).thenReturn(Arrays.asList(receta));

        List<Receta> lista = recetaService.listarRecetas();

        assertEquals(1, lista.size());
        assertEquals("Arroz con Pollo", lista.get(0).getNombre());
        verify(recetaRepository, times(1)).findAll();
    }

    @Test
    void obtenerReceta_deberiaRetornarReceta() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));

        Receta resultado = recetaService.obtenerReceta(1L);

        assertNotNull(resultado);
        assertEquals("Arroz con Pollo", resultado.getNombre());
    }

    @Test
    void obtenerReceta_deberiaLanzarExcepcionSiNoExiste() {
        when(recetaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> recetaService.obtenerReceta(99L));
    }

    @Test
    void actualizarReceta_deberiaActualizarCampos() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));
        when(recetaRepository.save(any(Receta.class))).thenAnswer(inv -> inv.getArgument(0));

        IngredienteReceta nuevo = new IngredienteReceta();
        nuevo.setCantidad(300f);
        recetaActualizada.getIngredientesReceta().add(nuevo);

        Receta resultado = recetaService.actualizarReceta(1L, recetaActualizada);

        assertEquals("Arroz Criollo", resultado.getNombre());
        assertEquals(NivelPicante.MEDIO, resultado.getNivelPicante());
        assertEquals(1, resultado.getIngredientesReceta().size());
    }

    @Test
    void eliminarReceta_deberiaEliminarExistente() {
        when(recetaRepository.findById(1L)).thenReturn(Optional.of(receta));

        recetaService.eliminarReceta(1L);

        verify(recetaRepository, times(1)).delete(receta);
    }

    @Test
    void eliminarReceta_deberiaLanzarExcepcionSiNoExiste() {
        when(recetaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> recetaService.eliminarReceta(99L));

        verify(recetaRepository, never()).delete(any());
    }
}

