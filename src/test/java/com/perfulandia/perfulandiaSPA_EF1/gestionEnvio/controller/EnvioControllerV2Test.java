package com.perfulandia.perfulandiaSPA_EF1.gestionEnvio.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.assemblers.EnvioModelAssembler;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller.EnvioControllerV2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Collections;

import org.springframework.hateoas.EntityModel;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnvioControllerV2.class)
public class EnvioControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnvioService envioService;

    @MockBean
    private EnvioModelAssembler envioAssembler;

    @Test
    void testListarEnvios_devuelveStatus200() throws Exception {
        Envio envio = new Envio();
        EntityModel<Envio> envioModel = EntityModel.of(envio);
        
        when(envioService.buscarTodosEnvios()).thenReturn(List.of(envio));
        when(envioAssembler.toModel(any(Envio.class))).thenReturn(envioModel);

        mockMvc.perform(get("/api/v2/gestionEnvios/envios"))
                .andExpect(status().isOk());
    }

    @Test
    void testListarEnvios_listaVacia_devuelveStatus204() throws Exception {
        when(envioService.buscarTodosEnvios()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/gestionEnvios/envios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarEnvioPorId_devuelveStatus200() throws Exception {
        Envio envio = new Envio();
        envio.setIdEnvio(1);
        EntityModel<Envio> envioModel = EntityModel.of(envio);
        
        when(envioService.buscarEnvioPorId(1)).thenReturn(envio);
        when(envioAssembler.toModel(any(Envio.class))).thenReturn(envioModel);

        mockMvc.perform(get("/api/v2/gestionEnvios/envios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarEnvioPorId_envioNoExiste_devuelveStatus404() throws Exception {
        when(envioService.buscarEnvioPorId(999)).thenReturn(null);

        mockMvc.perform(get("/api/v2/gestionEnvios/envios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarEnvio_devuelveStatus204() throws Exception {
        Envio envio = new Envio();
        envio.setIdEnvio(1);
        when(envioService.buscarEnvioPorId(1)).thenReturn(envio);

        mockMvc.perform(delete("/api/v2/gestionEnvios/envios/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarEnvio_envioNoExiste_devuelveStatus404() throws Exception {
        when(envioService.buscarEnvioPorId(999)).thenReturn(null);

        mockMvc.perform(delete("/api/v2/gestionEnvios/envios/delete/999"))
                .andExpect(status().isNotFound());
    }
}