package com.perfulandia.perfulandiaSPA_EF1.gestionEnvio.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;

public class EnvioModelTest {

    @Test
    void testCrearEnvioYVerificarLosDatos() {
        Envio envio = new Envio();

        envio.setIdEnvio(1);
        envio.setFechaEnvio("2023-10-01");
        envio.setEstadoEnvio("Enviado");
        envio.setCostoEnvio(10.0);
        envio.setFechaEntregaEstimada("2023-10-05");
        envio.setFechaEntregaFinal("2023-10-06");

        assertEquals(1, envio.getIdEnvio());
        assertEquals("2023-10-01", envio.getFechaEnvio());
        assertEquals("Enviado", envio.getEstadoEnvio());
        assertEquals(10.0, envio.getCostoEnvio());
        assertEquals("2023-10-05", envio.getFechaEntregaEstimada());
        assertEquals("2023-10-06", envio.getFechaEntregaFinal());
    }
}
