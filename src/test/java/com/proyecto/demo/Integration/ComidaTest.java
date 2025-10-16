package com.proyecto.demo.Integration;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.Macronutriente;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.repository.RecetaRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ComidaTest {

    @Autowired
    private ComidaRepository comidaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    //Crear comida y verificar persistencia
    @Test
    void testCrearComida_DeberiaPersistirEnBaseDeDatos() {
        // Arrange: crear una receta asociada
        Receta receta = new Receta();
        receta.setNombre("Pasta al pesto");
        receta.setDescripcion("Pasta con albahaca y piñones");
        receta.setMacronutriente(Macronutriente.CARBOHIDRATO);
        recetaRepository.save(receta);

        Comida comida = new Comida();
        comida.setHora(LocalTime.of(12, 30));
        comida.setReceta(receta);

        // Act
        Comida guardada = comidaRepository.save(comida);

        // Assert
        Optional<Comida> encontrada = comidaRepository.findById(guardada.getId());
        assertTrue(encontrada.isPresent(), "La comida debería haberse guardado en la base de datos");
        assertEquals(LocalTime.of(12, 30), encontrada.get().getHora());
        assertEquals("Pasta al pesto", encontrada.get().getReceta().getNombre());
    }

    //Actualizar comida y verificar cambios
    @Test
    void testActualizarComida_DeberiaGuardarCambiosEnBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Sopa de lentejas");
        receta.setDescripcion("Sopa rica en proteínas vegetales");
        receta.setMacronutriente(Macronutriente.PROTEINA);
        recetaRepository.save(receta);

        Comida comida = new Comida();
        comida.setHora(LocalTime.of(8, 0));
        comida.setReceta(receta);
        comidaRepository.save(comida);

        // Act
        comida.setHora(LocalTime.of(9, 15));
        comidaRepository.save(comida);

        // Assert
        Optional<Comida> actualizada = comidaRepository.findById(comida.getId());
        assertTrue(actualizada.isPresent(), "La comida actualizada debería existir");
        assertEquals(LocalTime.of(9, 15), actualizada.get().getHora(), "La hora no se actualizó correctamente");
    }

    //Eliminar comida y verificar eliminación
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
        comida.setReceta(receta);
        comidaRepository.save(comida);

        Long id = comida.getId();

        // Act
        comidaRepository.deleteById(id);

        // Assert
        Optional<Comida> eliminada = comidaRepository.findById(id);
        assertFalse(eliminada.isPresent(), "La comida debería haber sido eliminada de la base de datos");
    }
}
