package com.perfulandia.perfulandiaSPA_EF1.microservicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller.EnvioController;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
@ExtendWith(MockitoExtension.class)
public class EnvioMicroservicioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Envio envioTest;
    private Usuario usuarioTest;

    @BeforeEach
    void setUp() {
        // Configurar usuario de prueba
        usuarioTest = new Usuario();
        usuarioTest.setIdUsuario(1);
        usuarioTest.setNombre("Juan Pérez");
        usuarioTest.setCorreo("juan@example.com");

        // Configurar envío de prueba
        envioTest = new Envio();
        envioTest.setIdEnvio(1);
        envioTest.setUsuario(usuarioTest);
        envioTest.setFechaEnvio("2024-01-15");
        envioTest.setEstadoEnvio("EN_TRANSITO");
        envioTest.setCostoEnvio(25.50);
        envioTest.setFechaEntregaEstimada("2024-01-20");
        envioTest.setFechaEntregaFinal("2024-01-19");
    }

    @Test
    void testGuardarEnvio_DeberiaCrearEnvioExitosamente() throws Exception {
        // Given
        when(envioService.save(any(Envio.class))).thenReturn(envioTest);

        // When & Then
        mockMvc.perform(post("/api/gestionEnvios/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envioTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnvio").value(1))
                .andExpect(jsonPath("$.estadoEnvio").value("EN_TRANSITO"))
                .andExpect(jsonPath("$.costoEnvio").value(25.50));

        verify(envioService, times(1)).save(any(Envio.class));
    }

    @Test
    void testListarEnvios_DeberiaRetornarListaDeEnvios() throws Exception {
        // Given
        Envio envio2 = new Envio();
        envio2.setIdEnvio(2);
        envio2.setUsuario(usuarioTest);
        envio2.setFechaEnvio("2024-01-16");
        envio2.setEstadoEnvio("ENTREGADO");
        envio2.setCostoEnvio(35.75);

        List<Envio> envios = Arrays.asList(envioTest, envio2);
        when(envioService.buscarTodosEnvios()).thenReturn(envios);

        // When & Then
        mockMvc.perform(get("/api/gestionEnvios/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(envioService, times(1)).buscarTodosEnvios();
    }

    @Test
    void testListarEnvios_DeberiaRetornarNoContent_CuandoNoHayEnvios() throws Exception {
        // Given
        when(envioService.buscarTodosEnvios()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/gestionEnvios/envios"))
                .andExpect(status().isNoContent());

        verify(envioService, times(1)).buscarTodosEnvios();
    }

    @Test
    void testBuscarEnvioPorId_DeberiaRetornarEnvio_CuandoExiste() throws Exception {
        // Given
        when(envioService.buscarEnvioPorId(eq(1))).thenReturn(envioTest);

        // When & Then
        mockMvc.perform(get("/api/gestionEnvios/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnvio").value(1))
                .andExpect(jsonPath("$.estadoEnvio").value("EN_TRANSITO"));

        verify(envioService, times(1)).buscarEnvioPorId(eq(1));
    }

    @Test
    void testBuscarEnvioPorId_DeberiaRetornarNotFound_CuandoNoExiste() throws Exception {
        // Given
        when(envioService.buscarEnvioPorId(eq(99))).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/gestionEnvios/envios/99"))
                .andExpect(status().isNotFound());

        verify(envioService, times(1)).buscarEnvioPorId(eq(99));
    }

    @Test
    void testActualizarEnvio_DeberiaActualizarExitosamente() throws Exception {
        // Given
        Envio envioActualizado = new Envio();
        envioActualizado.setIdEnvio(1);
        envioActualizado.setUsuario(usuarioTest);
        envioActualizado.setFechaEnvio("2024-01-15");
        envioActualizado.setEstadoEnvio("ENTREGADO");
        envioActualizado.setCostoEnvio(25.50);

        when(envioService.update(any(Envio.class))).thenReturn(envioActualizado);

        // When & Then
        mockMvc.perform(put("/api/gestionEnvios/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(envioActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoEnvio").value("ENTREGADO"));

        verify(envioService, times(1)).update(any(Envio.class));
    }

    @Test
    void testEliminarEnvio_DeberiaEliminarExitosamente() throws Exception {
        // Given
        doNothing().when(envioService).delete(eq(1));

        // When & Then
        mockMvc.perform(delete("/api/gestionEnvios/envios/delete/1"))
                .andExpect(status().isNoContent());

        verify(envioService, times(1)).delete(eq(1));
    }
}
