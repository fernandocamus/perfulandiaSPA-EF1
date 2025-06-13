package com.perfulandia.perfulandiaSPA_EF1.gestionEnvio.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.repository.EnvioRepository;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;

@ExtendWith(MockitoExtension.class)
public class EnvioRepositoryTest {

    @Mock
    private EnvioRepository envioRepository;

    @Test
    void buscarPorIdEnvio_retornaEnvioCorrecto() {
        
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(10);
        usuario.setNombre("Carlos");
        usuario.setCorreo("carlos@correo.com");
        usuario.setContrasena("1234");

        Envio envio = new Envio();
        envio.setIdEnvio(1);
        envio.setUsuario(usuario);
        envio.setFechaEnvio("2025-06-13");
        envio.setEstadoEnvio("En tránsito");
        envio.setCostoEnvio(3500.0);
        envio.setFechaEntregaEstimada("2025-06-15");
        envio.setFechaEntregaFinal("2025-06-16");

        when(envioRepository.buscarPorIdEnvio("1")).thenReturn(List.of(envio));

 
        List<Envio> resultado = envioRepository.buscarPorIdEnvio("1");


        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("En tránsito", resultado.get(0).getEstadoEnvio());
        assertEquals(3500.0, resultado.get(0).getCostoEnvio());

        verify(envioRepository).buscarPorIdEnvio("1");
    }
}
