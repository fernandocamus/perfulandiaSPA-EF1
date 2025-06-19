package com.perfulandia.perfulandiaSPA_EF1.gestionUsuario.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.service.UsuarioService;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.assemblers.UsuarioModelAssembler;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.controller.UsuarioControllerV2;

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

@WebMvcTest(UsuarioControllerV2.class)
public class UsuarioControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioModelAssembler usuarioAssembler;

    @Test
    void testListarUsuarios_devuelveStatus200() throws Exception {
        Usuario usuario = new Usuario();
        EntityModel<Usuario> usuarioModel = EntityModel.of(usuario);
        
        when(usuarioService.buscarTodosUsuarios()).thenReturn(List.of(usuario));
        when(usuarioAssembler.toModel(any(Usuario.class))).thenReturn(usuarioModel);

        mockMvc.perform(get("/api/v2/gestionUsuarios/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void testListarUsuarios_listaVacia_devuelveStatus204() throws Exception {
        when(usuarioService.buscarTodosUsuarios()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/gestionUsuarios/usuarios"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarUsuarioPorId_devuelveStatus200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        EntityModel<Usuario> usuarioModel = EntityModel.of(usuario);
        
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);
        when(usuarioAssembler.toModel(any(Usuario.class))).thenReturn(usuarioModel);

        mockMvc.perform(get("/api/v2/gestionUsuarios/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarUsuarioPorId_usuarioNoExiste_devuelveStatus404() throws Exception {
        when(usuarioService.buscarUsuarioPorId(999)).thenReturn(null);

        mockMvc.perform(get("/api/v2/gestionUsuarios/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarUsuario_devuelveStatus204() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        mockMvc.perform(delete("/api/v2/gestionUsuarios/usuarios/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarUsuario_usuarioNoExiste_devuelveStatus404() throws Exception {
        when(usuarioService.buscarUsuarioPorId(999)).thenReturn(null);

        mockMvc.perform(delete("/api/v2/gestionUsuarios/usuarios/delete/999"))
                .andExpect(status().isNotFound());
    }
}