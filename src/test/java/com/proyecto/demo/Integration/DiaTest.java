package com.proyecto.demo.Integration;

import com.proyecto.demo.model.Comida;
import com.proyecto.demo.model.Dia;
import com.proyecto.demo.model.Receta;
import com.proyecto.demo.model.Macronutriente;
import com.proyecto.demo.service.DiaService;

import jakarta.transaction.Transactional;

import com.proyecto.demo.repository.DiaRepository;
import com.proyecto.demo.repository.RecetaRepository;
import com.proyecto.demo.repository.ComidaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class DiaTest {

    @Autowired
    private DiaService diaService;

    @Autowired
    private DiaRepository diaRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private ComidaRepository comidaRepository;

    //Crear día y verificar persistencia
    @Test
    void testCrearDia_DeberiaPersistirEnBaseDeDatos() {
        // Arrange
        Dia dia = new Dia();
        dia.setFecha(LocalDate.of(2025, 10, 16));

        // Act
        Dia guardado = diaService.crearDia(dia);

        // Assert
        Optional<Dia> encontrado = diaRepository.findById(guardado.getId());
        assertTrue(encontrado.isPresent(), "El día debería haberse guardado en la base de datos");
    }

    //Actualizar día y verificar cambios
    @Test
    void testActualizarDia_DeberiaGuardarCambiosEnBaseDeDatos() {
        // Arrange
        Dia dia = new Dia();
        dia.setFecha(LocalDate.of(2025, 10, 15));
        diaService.crearDia(dia);

        Dia diaActualizado = new Dia();
        diaActualizado.setFecha(LocalDate.of(2025, 10, 17));

        // Act
        diaService.actualizarDia(dia.getId(), diaActualizado);

        // Assert
        Dia actualizado = diaService.obtenerDia(dia.getId());
        assertEquals(LocalDate.of(2025, 10, 17), actualizado.getFecha(), "La fecha del día no se actualizó correctamente");
    }

    //Eliminar día y verificar eliminación
    @Test
    void testEliminarDia_DeberiaRemoverDeBaseDeDatos() {
        // Arrange
        Dia dia = new Dia();
        dia.setFecha(LocalDate.of(2025, 10, 18));
        diaService.crearDia(dia);
        Long id = dia.getId();

        // Act
        diaService.eliminarDia(id);

        // Assert
        Optional<Dia> eliminado = diaRepository.findById(id);
        assertFalse(eliminado.isPresent(), "El día debería haber sido eliminado de la base de datos");
    }

    //Agregar comida a un día y verificar relación con receta
    @Test
    void testAgregarComidaADia_DeberiaCrearRelacionCorrectamente() {
        // Arrange
        Dia dia = new Dia();
        dia.setFecha(LocalDate.of(2025, 10, 19));
        diaService.crearDia(dia);

        Receta receta = new Receta();
        receta.setNombre("Tostadas con aguacate");
        receta.setDescripcion("Pan integral con aguacate y huevo");
        receta.setMacronutriente(Macronutriente.GRASA);
        recetaRepository.save(receta);

        // Act
        Comida comida = diaService.agregarComidaADia(dia.getId(), receta.getId(), LocalTime.of(8, 30));

        // Assert
        assertNotNull(comida.getId(), "La comida debería haberse guardado correctamente");
        assertEquals(LocalTime.of(8, 30), comida.getHora(), "La hora de la comida no coincide");
        assertEquals(receta.getId(), comida.getReceta().getId(), "La receta asociada no coincide");

        List<Comida> comidasDelDia = diaService.listarComidasDeDia(dia.getId());
        assertEquals(1, comidasDelDia.size(), "El día debería tener exactamente una comida asociada");
    }
}
