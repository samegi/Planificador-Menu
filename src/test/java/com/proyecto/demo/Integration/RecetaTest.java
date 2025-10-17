package com.proyecto.demo.Integration;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.Macronutriente;
import com.proyecto.demo.repository.RecetaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class RecetaTest {

    @Autowired
    private RecetaRepository recetaRepository;

    //Crear receta y verificar persistencia
    @Test
    void testCrearReceta_DeberiaPersistirEnBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Ensalada de Quinoa");
        receta.setDescripcion("Una ensalada nutritiva con quinoa, aguacate y garbanzos.");
        receta.setMacronutriente(Macronutriente.PROTEINA);

        // Act
        Receta guardada = recetaRepository.save(receta);
        Optional<Receta> encontrada = recetaRepository.findById(guardada.getId());

        // Assert
        assertTrue(encontrada.isPresent(), "La receta debería haberse guardado en la base de datos");
        assertEquals("Ensalada de Quinoa", encontrada.get().getNombre());
    }

    //Actualizar receta y verificar cambios
    @Test
    void testActualizarReceta_DeberiaGuardarCambiosEnBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Tortilla de Espinaca");
        receta.setDescripcion("Receta original.");
        receta.setMacronutriente(Macronutriente.GRASA);
        Receta guardada = recetaRepository.save(receta);

        // Act
        guardada.setDescripcion("Tortilla de espinaca con queso bajo en grasa.");
        guardada.setMacronutriente(Macronutriente.PROTEINA);
        recetaRepository.save(guardada);

        Optional<Receta> actualizada = recetaRepository.findById(guardada.getId());

        // Assert
        assertTrue(actualizada.isPresent(), "La receta actualizada debería existir en la base de datos");
        assertEquals("Tortilla de espinaca con queso bajo en grasa.", actualizada.get().getDescripcion());
        assertEquals(Macronutriente.PROTEINA, actualizada.get().getMacronutriente());
    }

    //Eliminar receta y verificar eliminación
    
    @Test
    void testEliminarReceta_DeberiaRemoverDeBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Smoothie Verde");
        receta.setDescripcion("Smoothie con espinaca, manzana y jengibre.");
        receta.setMacronutriente(Macronutriente.CARBOHIDRATO);
        Receta guardada = recetaRepository.save(receta);

        Long id = guardada.getId();

        // Act
        recetaRepository.deleteById(id);
        Optional<Receta> eliminada = recetaRepository.findById(id);

        // Assert
        assertFalse(eliminada.isPresent(), "La receta debería haber sido eliminada de la base de datos");
    }
}
