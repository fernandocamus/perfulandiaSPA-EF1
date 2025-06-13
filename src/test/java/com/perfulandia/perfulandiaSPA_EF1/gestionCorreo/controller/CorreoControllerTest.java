package com.perfulandia.perfulandiaSPA_EF1.gestionCorreo.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller.CorreoController;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CorreoController.class)
public class CorreoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorreoService correoService;

    @Test
    void testListarCorreos_devuelveStatus200() throws Exception {
        Correo correo = new Correo();
        when(correoService.buscarTodosCorreos()).thenReturn(List.of(correo));

        mockMvc.perform(get("/api/gestionCorreos/correos"))
                .andExpect(status().isOk());
    }
}
