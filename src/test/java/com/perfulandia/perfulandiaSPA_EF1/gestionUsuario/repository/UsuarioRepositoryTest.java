package com.perfulandia.perfulandiaSPA_EF1.gestionUsuario.repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void buscarPorIdUsuario_retornaUsuarioCorrecto() {
        // Given
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        // Agrega otros campos del Usuario según tu modelo
        // usuario.setNombre("Juan Pérez");
        // usuario.setEmail("juan@example.com");

        when(usuarioRepository.buscarPorIdUsuario(1)).thenReturn(List.of(usuario));

        // When
        List<Usuario> resultado = usuarioRepository.buscarPorIdUsuario(1);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdUsuario());
        // assertEquals("Juan Pérez", resultado.get(0).getNombre());
        verify(usuarioRepository).buscarPorIdUsuario(1);
    }

    @Test
    void buscarPorIdUsuario_conIdInexistente_retornaListaVacia() {
        // Given
        when(usuarioRepository.buscarPorIdUsuario(999)).thenReturn(List.of());

        // When
        List<Usuario> resultado = usuarioRepository.buscarPorIdUsuario(999);

        // Then
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).buscarPorIdUsuario(999);
    }

    @Test
    void buscarPorIdUsuario_conIdNull_retornaListaVacia() {
        // Given
        when(usuarioRepository.buscarPorIdUsuario(null)).thenReturn(List.of());

        // When
        List<Usuario> resultado = usuarioRepository.buscarPorIdUsuario(null);

        // Then
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository).buscarPorIdUsuario(null);
    }

    @Test
    void buscarPorIdUsuario_verificaLlamadaCorrecta() {
        // Given
        Integer idUsuario = 5;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        when(usuarioRepository.buscarPorIdUsuario(idUsuario)).thenReturn(List.of(usuario));

        // When
        List<Usuario> resultado = usuarioRepository.buscarPorIdUsuario(idUsuario);

        // Then
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(idUsuario, resultado.get(0).getIdUsuario());
        verify(usuarioRepository, times(1)).buscarPorIdUsuario(idUsuario);
    }
}