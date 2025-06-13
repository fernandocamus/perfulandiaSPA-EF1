package com.perfulandia.perfulandiaSPA_EF1.gestionEnvio.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller.EnvioController;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnvioController.class)
public class EnvioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @Test
    void testGuardarEnvio() throws Exception {
        Envio envio = new Envio();
        envio.setIdEnvio(1);

        when(envioService.save(envio)).thenReturn(envio);

        mockMvc.perform(post("/api/gestionEnvios/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idEnvio\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnvio").value(1));
    }

    @Test
    void testListarEnvios() throws Exception {
        Envio envio1 = new Envio();
        Envio envio2 = new Envio();
        List<Envio> envios = Arrays.asList(envio1, envio2);

        when(envioService.buscarTodosEnvios()).thenReturn(envios);

        mockMvc.perform(get("/api/gestionEnvios/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testBuscarEnvioPorId() throws Exception {
        Envio envio = new Envio();
        envio.setIdEnvio(1);

        when(envioService.buscarEnvioPorId(1)).thenReturn(envio);

        mockMvc.perform(get("/api/gestionEnvios/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnvio").value(1));
    }

    @Test
    void testActualizarEnvio() throws Exception {
        Envio envio = new Envio();
        envio.setIdEnvio(1);

        when(envioService.update(envio)).thenReturn(envio);

        mockMvc.perform(put("/api/gestionEnvios/envios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idEnvio\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEnvio").value(1));
    }

    @Test
    void testEliminarEnvio() throws Exception {
        mockMvc.perform(delete("/api/gestionEnvios/envios/delete/1"))
                .andExpect(status().isNoContent());
    }

    public EnvioService getEnvioService() {
        return envioService;
    }

    public void setEnvioService(EnvioService envioService) {
        this.envioService = envioService;
    }
}
