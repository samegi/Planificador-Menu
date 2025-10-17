package com.proyecto.demo.Integration;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.Macronutriente;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.service.ComidaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ComidaTest {

    @Autowired
    private ComidaService comidaService;

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    // Crear comida y verificar persistencia
    @Test
    void testCrearComida_DeberiaPersistirEnBaseDeDatos() {
        // Arrange: crear y guardar una receta asociada
        Receta receta = new Receta();
        receta.setNombre("Pasta al pesto");
        receta.setDescripcion("Pasta con albahaca y piñones");
        receta.setMacronutriente(Macronutriente.CARBOHIDRATO);
        recetaRepository.save(receta);

        // Crear la comida (sin asignar la receta todavía)
        Comida comida = new Comida();
        comida.setHora(LocalTime.of(12, 30));

        // Act: crear la comida usando el servicio con el ID de la receta
        Comida guardada = comidaService.crearComida(comida, receta.getId());

        // Assert
        Optional<Comida> encontrada = comidaRepository.findById(guardada.getId());
        assertTrue(encontrada.isPresent(), "La comida debería haberse guardado en la base de datos");
        assertEquals(LocalTime.of(12, 30), encontrada.get().getHora(), "La hora no coincide");
        assertNotNull(encontrada.get().getReceta(), "La comida debería tener una receta asociada");
        assertEquals("Pasta al pesto", encontrada.get().getReceta().getNombre(), "El nombre de la receta asociada no coincide");
    }

    // Actualizar comida y verificar cambios
    @Test
    void testActualizarComida_DeberiaGuardarCambiosEnBaseDeDatos() {
        // Arrange: primero creamos una receta base
        Receta receta = new Receta();
        receta.setNombre("Sopa de lentejas");
        receta.setDescripcion("Sopa rica en proteínas vegetales");
        receta.setMacronutriente(Macronutriente.PROTEINA);
        recetaRepository.save(receta);

        // Crear una comida con esa receta
        Comida comida = new Comida();
        comida.setHora(LocalTime.of(8, 0));
        comidaService.crearComida(comida, receta.getId());

        // Act: crear un objeto con los nuevos datos para actualizar
        Comida comidaActualizada = new Comida();
        comidaActualizada.setHora(LocalTime.of(9, 15));
        comidaActualizada.setReceta(receta);

        comidaService.actualizarComida(comida.getId(), comidaActualizada);

        // Assert
        Optional<Comida> actualizada = comidaRepository.findById(comida.getId());
        assertTrue(actualizada.isPresent(), "La comida actualizada debería existir en la base de datos");
        assertEquals(LocalTime.of(9, 15), actualizada.get().getHora(), "La hora no se actualizó correctamente");
    }


    // Eliminar comida y verificar eliminación
    @Test
    void testEliminarComida_DeberiaRemoverDeBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Ensalada César");
        receta.setDescripcion("Clásica ensalada con pollo y aderezo césar");
        receta.setMacronutriente(Macronutriente.GRASA);
        recetaRepository.save(receta);

        Comida comida = new Comida();
        comida.setHora(LocalTime.of(19, 0));
        comidaService.crearComida(comida, receta.getId());

        Long id = comida.getId();

        // Act
        comidaService.eliminarComida(id);

        // Assert
        Optional<Comida> eliminada = comidaRepository.findById(id);
        assertFalse(eliminada.isPresent(), "La comida debería haber sido eliminada de la base de datos");
    }

}
