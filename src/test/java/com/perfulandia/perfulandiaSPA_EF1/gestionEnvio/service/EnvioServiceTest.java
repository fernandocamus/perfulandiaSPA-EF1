package com.perfulandia.perfulandiaSPA_EF1.gestionEnvio.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.repository.EnvioRepository;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;

public class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @InjectMocks
    private EnvioService envioService;

    public EnvioServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodosEnvios() {
        Envio envio1 = new Envio();
        envio1.setIdEnvio(1);
        envio1.setFechaEnvio("2023-10-01");

        Envio envio2 = new Envio();
        envio2.setIdEnvio(2);
        envio2.setFechaEnvio("2023-10-02");

        when(envioRepository.findAll()).thenReturn(Arrays.asList(envio1, envio2));

        List<Envio> resultado = envioService.buscarTodosEnvios();

        assertEquals(2, resultado.size());
        assertEquals("2023-10-01", resultado.get(0).getFechaEnvio());
        verify(envioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarEnvioPorId() {
        Envio envio = new Envio();
        envio.setIdEnvio(1);
        envio.setFechaEnvio("2023-10-01");

        when(envioRepository.findById(1)).thenReturn(Optional.of(envio));

        Envio resultado = envioService.buscarEnvioPorId(1);

        assertNotNull(resultado);
        assertEquals("2023-10-01", resultado.getFechaEnvio());
        verify(envioRepository, times(1)).findById(1);
    }

    @Test
    void testSaveEnvio() {
        Envio envio = new Envio();
        envio.setIdEnvio(1);
        envio.setFechaEnvio("2023-10-01");

        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.save(envio);

        assertNotNull(resultado);
        assertEquals("2023-10-01", resultado.getFechaEnvio());
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testUpdateEnvio() {
        Envio envio = new Envio();
        envio.setIdEnvio(1);
        envio.setFechaEnvio("2023-10-01");

        when(envioRepository.existsById(1)).thenReturn(true);
        when(envioRepository.save(envio)).thenReturn(envio);

        Envio resultado = envioService.update(envio);

        assertNotNull(resultado);
        assertEquals("2023-10-01", resultado.getFechaEnvio());
        verify(envioRepository, times(1)).existsById(1);
        verify(envioRepository, times(1)).save(envio);
    }

    @Test
    void testDeleteEnvio() {
        doNothing().when(envioRepository).deleteById(1);

        envioService.delete(1);

        verify(envioRepository, times(1)).deleteById(1);
    }
}
