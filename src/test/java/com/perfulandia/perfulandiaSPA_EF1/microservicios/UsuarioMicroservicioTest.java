package com.perfulandia.perfulandiaSPA_EF1.microservicios;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = com.perfulandia.perfulandiaSPA_EF1.PerfulandiaSpaEf1Application.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UsuarioMicroservicioTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void flujoCompletoUsuario_postThenGet() throws Exception {
        String nuevoJson = """
            {
                "apodo":"dil",
                "nombre":"dilan",
                "apellido":"fuentes",
                "fechaNacimiento":"1990-02-02",
                "correo":"juan@correo.com",
                "contrasena":"12345",
                "direccion":"Santiago",
                "comuna":"San Ramon",
                "ciudad":"Santiago",
                "region":"Metropolitana"
            }
        """;

        mockMvc.perform(post("/api/gestionUsuarios/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevoJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario", notNullValue()));

        mockMvc.perform(get("/api/gestionUsuarios/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].correo", is("juan@correo.com")));
    }
}
