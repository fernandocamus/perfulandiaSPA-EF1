package com.perfulandia.perfulandiaSPA_EF1.gestionCorreo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.assemblers.CorreoModelAssembler;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller.CorreoControllerV2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CorreoControllerV2.class)
public class CorreoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorreoService correoService;

    @MockBean
    private CorreoModelAssembler correoAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarCorreos_devuelveStatus200() throws Exception {
        Correo correo = new Correo();
        EntityModel<Correo> correoModel = EntityModel.of(correo);

        when(correoService.buscarTodosCorreos()).thenReturn(List.of(correo));
        when(correoAssembler.toModel(any(Correo.class))).thenReturn(correoModel);

        mockMvc.perform(get("/api/v2/gestionCorreos/correos"))
                .andExpect(status().isOk());
    }

    @Test
    void testListarCorreos_listaVacia_devuelveStatus204() throws Exception {
        when(correoService.buscarTodosCorreos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/gestionCorreos/correos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarCorreoPorId_devuelveStatus200() throws Exception {
        Correo correo = new Correo();
        correo.setIdCorreo(1);
        EntityModel<Correo> correoModel = EntityModel.of(correo);

        when(correoService.buscarCorreoPorId(1)).thenReturn(correo);
        when(correoAssembler.toModel(any(Correo.class))).thenReturn(correoModel);

        mockMvc.perform(get("/api/v2/gestionCorreos/correos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarCorreoPorId_noExiste_devuelveStatus404() throws Exception {
        when(correoService.buscarCorreoPorId(999)).thenReturn(null);

        mockMvc.perform(get("/api/v2/gestionCorreos/correos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCorreo_devuelveStatus204() throws Exception {
        Correo correo = new Correo();
        correo.setIdCorreo(1);
        when(correoService.buscarCorreoPorId(1)).thenReturn(correo);
        doNothing().when(correoService).delete(1);

        mockMvc.perform(delete("/api/v2/gestionCorreos/correos/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarCorreo_noExiste_devuelveStatus404() throws Exception {
        when(correoService.buscarCorreoPorId(999)).thenReturn(null);

        mockMvc.perform(delete("/api/v2/gestionCorreos/correos/delete/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarEnviarCorreo_devuelveStatus200() throws Exception {
        Correo correo = new Correo();
        EntityModel<Correo> correoModel = EntityModel.of(correo);

        when(correoAssembler.toModel(any(Correo.class))).thenReturn(correoModel);
        doNothing().when(correoService).enviarCorreo(any(Correo.class));
        when(correoService.save(any(Correo.class))).thenReturn(correo);

        mockMvc.perform(post("/api/v2/gestionCorreos/correos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correo)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarCorreo_devuelveStatus200() throws Exception {
        Correo correo = new Correo();
        correo.setIdCorreo(1);
        EntityModel<Correo> correoModel = EntityModel.of(correo);

        when(correoService.update(any(Correo.class))).thenReturn(correo);
        when(correoAssembler.toModel(any(Correo.class))).thenReturn(correoModel);
        doNothing().when(correoService).enviarCorreo(any(Correo.class));

        mockMvc.perform(put("/api/v2/gestionCorreos/correos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correo)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarCorreo_noExiste_devuelveStatus404() throws Exception {
        Correo correo = new Correo();
        correo.setIdCorreo(999);

        when(correoService.update(any(Correo.class))).thenReturn(null);

        mockMvc.perform(put("/api/v2/gestionCorreos/correos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correo)))
                .andExpect(status().isNotFound());
    }
}
