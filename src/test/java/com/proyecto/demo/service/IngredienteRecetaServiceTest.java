package com.proyecto.demo.service;

import com.proyecto.demo.model.IngredienteReceta;
import com.proyecto.demo.model.Ingrediente;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.repository.IngredienteRecetaRepository;
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
class IngredienteRecetaServiceTest {

    @Mock
    private IngredienteRecetaRepository repo;

    @InjectMocks
    private IngredienteRecetaService service;

    private IngredienteReceta ingredienteReceta;

    @BeforeEach
    void setUp() {
        ingredienteReceta = new IngredienteReceta();
        ingredienteReceta.setId(1L);
        ingredienteReceta.setCantidad(2.5);

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(10L);
        ingrediente.setNombre("Sal");

        Receta receta = new Receta();
        receta.setId(20L);
        receta.setNombre("Guacamole");

        ingredienteReceta.setIngrediente(ingrediente);
        ingredienteReceta.setReceta(receta);
    }

    @Test
    void create_Exitoso() {
        when(repo.save(ingredienteReceta)).thenReturn(ingredienteReceta);

        IngredienteReceta result = service.create(ingredienteReceta);

        assertEquals(2.5, result.getCantidad());
        verify(repo).save(ingredienteReceta);
    }

    @Test
    void findById_Existente() {
        when(repo.findById(1L)).thenReturn(Optional.of(ingredienteReceta));

        IngredienteReceta result = service.findById(1L);

        assertEquals(2.5, result.getCantidad());
        assertEquals("Sal", result.getIngrediente().getNombre());
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
        when(repo.findAll()).thenReturn(List.of(ingredienteReceta));

        List<IngredienteReceta> result = service.findAll();

        assertEquals(1, result.size());
        verify(repo).findAll();
    }

    @Test
    void delete_Existente() {
        when(repo.findById(1L)).thenReturn(Optional.of(ingredienteReceta));
        doNothing().when(repo).delete(ingredienteReceta);

        service.delete(1L);

        verify(repo).delete(ingredienteReceta);
    }

    @Test
    void delete_NoExistenteLanzaExcepcion() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.delete(1L));
        verify(repo, never()).delete(any());
    }

    @Test
    void update_IngredienteRecetaExistente() {
        IngredienteReceta datos = new IngredienteReceta();
        datos.setCantidad(5.0);

        Ingrediente nuevoIngrediente = new Ingrediente();
        nuevoIngrediente.setId(30L);
        nuevoIngrediente.setNombre("Azúcar");

        Receta nuevaReceta = new Receta();
        nuevaReceta.setId(40L);
        nuevaReceta.setNombre("Salsa Verde");

        datos.setIngrediente(nuevoIngrediente);
        datos.setReceta(nuevaReceta);

        when(repo.findById(1L)).thenReturn(Optional.of(ingredienteReceta));
        when(repo.save(any(IngredienteReceta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        IngredienteReceta result = service.update(1L, datos);

        assertEquals(5.0, result.getCantidad());
        assertEquals("Azúcar", result.getIngrediente().getNombre());
        assertEquals("Salsa Verde", result.getReceta().getNombre());
        verify(repo).save(any(IngredienteReceta.class));
    }
}

