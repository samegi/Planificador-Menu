package com.proyecto.demo.Unit;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.proyecto.demo.model.Dia;
import com.proyecto.demo.service.DiaService;
import com.proyecto.demo.repository.ComidaRepository;
import com.proyecto.demo.repository.DiaRepository;
import com.proyecto.demo.repository.RecetaRepository;

class DiaServiceTest {

    @Mock DiaRepository diaRepository;
    @Mock ComidaRepository comidaRepository;
    @Mock RecetaRepository recetaRepository;

    @InjectMocks DiaService diaService;

    DiaServiceTest() { MockitoAnnotations.openMocks(this); }

    @Test
    void crearDia_deberiaGuardarYRetornarDia() {
        Dia dia = new Dia();
        dia.setFecha(LocalDate.of(2025, 10, 14));
        when(diaRepository.save(dia)).thenReturn(dia);

        Dia res = diaService.crearDia(dia);

        assertNotNull(res);
        assertEquals(LocalDate.of(2025, 10, 14), res.getFecha());
        verify(diaRepository).save(dia);
    }

    @Test
    void obtenerDia_existente_deberiaRetornarDia() {
        Dia dia = new Dia();
        dia.setId(1L);
        dia.setFecha(LocalDate.now());
        when(diaRepository.findById(1L)).thenReturn(Optional.of(dia));

        Dia res = diaService.obtenerDia(1L);

        assertEquals(1L, res.getId());
        verify(diaRepository).findById(1L);
    }
}
