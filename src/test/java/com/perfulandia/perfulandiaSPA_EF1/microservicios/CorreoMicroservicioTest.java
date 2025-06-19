package com.perfulandia.perfulandiaSPA_EF1.microservicios;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller.CorreoController;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;
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

@WebMvcTest(CorreoController.class)
@ExtendWith(MockitoExtension.class)

public class CorreoMicroservicioTest {
@Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorreoService correoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Correo correoTest;

    @BeforeEach
    void setUp() {
        correoTest = new Correo();
        correoTest.setIdCorreo(1);
        correoTest.setDestinatario("test@example.com");
        correoTest.setAsunto("Prueba");
        correoTest.setCuerpo("Contenido de prueba");
        correoTest.setArchivoAdjunto("/ruta/archivo.pdf");
    }

    @Test
    void testGuardarCorreo_DeberiaEnviarCorreo() throws Exception {
        when(correoService.save(any(Correo.class))).thenReturn(correoTest);
        doNothing().when(correoService).enviarCorreo(any(Correo.class));

        mockMvc.perform(post("/api/gestionCorreos/correos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correoTest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo enviado a: test@example.com"));

        verify(correoService).save(any(Correo.class));
        verify(correoService).enviarCorreo(any(Correo.class));
    }

    @Test
    void testListarCorreos_DeberiaRetornarLista() throws Exception {
        List<Correo> lista = Arrays.asList(correoTest);
        when(correoService.buscarTodosCorreos()).thenReturn(lista);

        mockMvc.perform(get("/api/gestionCorreos/correos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].destinatario").value("test@example.com"));

        verify(correoService).buscarTodosCorreos();
    }

    @Test
    void testListarCorreos_DeberiaRetornarNoContent() throws Exception {
        when(correoService.buscarTodosCorreos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/gestionCorreos/correos"))
                .andExpect(status().isNoContent());

        verify(correoService).buscarTodosCorreos();
    }

    @Test
    void testBuscarCorreoPorId_Existe() throws Exception {
        when(correoService.buscarCorreoPorId(eq(1))).thenReturn(correoTest);

        mockMvc.perform(get("/api/gestionCorreos/correos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destinatario").value("test@example.com"));

        verify(correoService).buscarCorreoPorId(eq(1));
    }

    @Test
    void testBuscarCorreoPorId_NoExiste() throws Exception {
        when(correoService.buscarCorreoPorId(eq(999))).thenReturn(null);

        mockMvc.perform(get("/api/gestionCorreos/correos/999"))
                .andExpect(status().isNotFound());

        verify(correoService).buscarCorreoPorId(eq(999));
    }

    @Test
    void testActualizarCorreo_DeberiaActualizarYEnviar() throws Exception {
        when(correoService.update(any(Correo.class))).thenReturn(correoTest);
        doNothing().when(correoService).enviarCorreo(any(Correo.class));

        mockMvc.perform(put("/api/gestionCorreos/correos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correoTest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo actualizado y enviado a: test@example.com"));

        verify(correoService).update(any(Correo.class));
        verify(correoService).enviarCorreo(any(Correo.class));
    }

    @Test
    void testActualizarCorreo_NoExiste() throws Exception {
        when(correoService.update(any(Correo.class))).thenReturn(null);

        mockMvc.perform(put("/api/gestionCorreos/correos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correoTest)))
                .andExpect(status().isNotFound());

        verify(correoService).update(any(Correo.class));
    }

    @Test
    void testEliminarCorreo_DeberiaEliminar() throws Exception {
        when(correoService.buscarCorreoPorId(eq(1))).thenReturn(correoTest);
        doNothing().when(correoService).delete(eq(1));

        mockMvc.perform(delete("/api/gestionCorreos/correos/delete/1"))
                .andExpect(status().isNoContent());

        verify(correoService).buscarCorreoPorId(eq(1));
        verify(correoService).delete(eq(1));
    }

    @Test
    void testEliminarCorreo_NoExiste() throws Exception {
        when(correoService.buscarCorreoPorId(eq(999))).thenReturn(null);

        mockMvc.perform(delete("/api/gestionCorreos/correos/delete/999"))
                .andExpect(status().isNotFound());

        verify(correoService).buscarCorreoPorId(eq(999));
    }
    
}
