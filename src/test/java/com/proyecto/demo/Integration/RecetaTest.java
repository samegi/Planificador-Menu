package com.proyecto.demo.Integration;

import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.Macronutriente;
import com.proyecto.demo.service.RecetaService;
import com.proyecto.demo.repository.RecetaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RecetaTest {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private RecetaRepository recetaRepository;

    // Crear receta y verificar persistencia
    @Test
    void testCrearReceta_DeberiaPersistirEnBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Ensalada de Quinoa");
        receta.setDescripcion("Una ensalada nutritiva con quinoa, aguacate y garbanzos.");
        receta.setMacronutriente(Macronutriente.PROTEINA);

        // Act
        recetaService.crearReceta(receta);

        // Assert
        Optional<Receta> encontrada = recetaRepository.findById(receta.getId());
        assertTrue(encontrada.isPresent(), "La receta debería haberse guardado en la base de datos");
        assertEquals("Ensalada de Quinoa", encontrada.get().getNombre());
    }

    // Actualizar receta y verificar cambios
    // Actualizar receta y verificar cambios (cambia descripción y macronutriente)
    @Test
    void testActualizarReceta_DeberiaGuardarCambiosEnBaseDeDatos() {
        // Arrange
        Receta base = new Receta();
        base.setNombre("Tortilla de Espinaca");
        base.setDescripcion("Receta original.");
        base.setMacronutriente(Macronutriente.GRASA);
        recetaService.crearReceta(base);

        // Construyo el objeto "recetaActualizada" (lo que llega por API/servicio)
        Receta cambios = new Receta();
        cambios.setNombre("Tortilla de Espinaca (light)");
        cambios.setDescripcion("Tortilla de espinaca con queso bajo en grasa.");
        cambios.setMacronutriente(Macronutriente.PROTEINA); // debe actualizarse

        // Act
        Receta result = recetaService.actualizarReceta(base.getId(), cambios);

        // Assert (releo desde BD para no depender del objeto en memoria)
        Optional<Receta> actualizada = recetaRepository.findById(base.getId());
        assertTrue(actualizada.isPresent(), "La receta actualizada debería existir en la base de datos");
        assertEquals("Tortilla de Espinaca (light)", actualizada.get().getNombre());
        assertEquals("Tortilla de espinaca con queso bajo en grasa.", actualizada.get().getDescripcion());
        assertEquals(Macronutriente.PROTEINA, actualizada.get().getMacronutriente());
    }


    // Eliminar receta y verificar eliminación
    @Test
    void testEliminarReceta_DeberiaRemoverDeBaseDeDatos() {
        // Arrange
        Receta receta = new Receta();
        receta.setNombre("Smoothie Verde");
        receta.setDescripcion("Smoothie con espinaca, manzana y jengibre.");
        receta.setMacronutriente(Macronutriente.CARBOHIDRATO);
        recetaService.crearReceta(receta);

        Long id = receta.getId();

        // Act
        recetaService.eliminarReceta(id);

        // Assert
        Optional<Receta> eliminada = recetaRepository.findById(id);
        assertFalse(eliminada.isPresent(), "La receta debería haber sido eliminada de la base de datos");
    }
}