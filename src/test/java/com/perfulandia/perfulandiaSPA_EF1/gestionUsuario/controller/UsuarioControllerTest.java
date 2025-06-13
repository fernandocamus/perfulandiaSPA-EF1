package com.perfulandia.perfulandiaSPA_EF1.gestionUsuario.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.controller.UsuarioController;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testListarUsuarios_devuelveStatus200() throws Exception {
        Usuario usuario = new Usuario();
        when(usuarioService.buscarTodosUsuarios()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/gestionUsuarios/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarUsuarioPorId_devuelveStatus200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        mockMvc.perform(get("/api/gestionUsuarios/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarUsuarioPorId_usuarioNoExiste_devuelveStatus404() throws Exception {
        when(usuarioService.buscarUsuarioPorId(999)).thenReturn(null);

        mockMvc.perform(get("/api/gestionUsuarios/usuarios/999"))
                .andExpect(status().isNotFound());
    }
}