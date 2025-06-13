package com.perfulandia.perfulandiaSPA_EF1.gestionUsuario.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.repository.UsuarioRepository;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setApodo("dil");
        usuario.setNombre("dilan");
        usuario.setApellido("fuentes");
        usuario.setFechaNacimiento("1990-02-02");
        usuario.setCorreo("dilan.dilan@gmail.com");
        usuario.setContrasena("123456");
        usuario.setDireccion("Calle 123");
        usuario.setComuna("Las Condes");
        usuario.setCiudad("Santiago");
        usuario.setRegion("Metropolitana");

        usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setApodo("fer");
        usuario2.setNombre("fernando");
        usuario2.setApellido("camus");
        usuario2.setFechaNacimiento("1985-10-10");
        usuario2.setCorreo("camus@gmail.com");
        usuario2.setContrasena("654321");
        usuario2.setDireccion("Avenida 456");
        usuario2.setComuna("Providencia");
        usuario2.setCiudad("Santiago");
        usuario2.setRegion("Metropolitana");
    }

    // TESTS PARA BUSCAR TODOS LOS USUARIOS
    @Test
    void testBuscarTodosUsuarios_devuelveListaConUsuarios() {
        // Given
        List<Usuario> usuariosEsperados = Arrays.asList(usuario, usuario2);
        when(usuarioRepository.findAll()).thenReturn(usuariosEsperados);

        // When
        List<Usuario> resultado = usuarioService.buscarTodosUsuarios();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(usuariosEsperados, resultado);
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testBuscarTodosUsuarios_devuelveListaVacia() {
        // Given
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Usuario> resultado = usuarioService.buscarTodosUsuarios();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(usuarioRepository, times(1)).findAll();
    }

    // TESTS PARA BUSCAR USUARIO POR ID
    @Test
    void testBuscarUsuarioPorId_usuarioExiste_devuelveUsuario() {
        // Given
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        // When
        Usuario resultado = usuarioService.buscarUsuarioPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("dil", resultado.getApodo());
        assertEquals("dilan", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarUsuarioPorId_usuarioNoExiste_lanzaExcepcion() {
        // Given
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(Exception.class, () -> {
            usuarioService.buscarUsuarioPorId(999);
        });
        verify(usuarioRepository, times(1)).findById(999);
    }

    // TESTS PARA GUARDAR USUARIO
    @Test
    void testSave_guardaUsuarioCorrectamente() {
        // Given
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // When
        Usuario resultado = usuarioService.save(usuario);

        // Then
        assertNotNull(resultado);
        assertEquals(usuario, resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("dil", resultado.getApodo());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testSave_conUsuarioNulo() {
        // Given
        when(usuarioRepository.save(null)).thenThrow(new IllegalArgumentException("Usuario no puede ser nulo"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.save(null);
        });
        verify(usuarioRepository, times(1)).save(null);
    }

    // TESTS PARA ACTUALIZAR USUARIO
    @Test
    void testUpdate_usuarioExiste_actualizaCorrectamente() {
        // Given
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setIdUsuario(1);
        usuarioActualizado.setApodo("dil_actualizado");
        usuarioActualizado.setNombre("dilan insaggi");
        usuarioActualizado.setApellido("fuentes silva");
        usuarioActualizado.setFechaNacimiento("1990-02-02");
        usuarioActualizado.setCorreo("dilan.dilan@gmail.com");
        usuarioActualizado.setContrasena("nuevapass123");
        usuarioActualizado.setDireccion("Nueva Direccion 789");
        usuarioActualizado.setComuna("Vitacura");
        usuarioActualizado.setCiudad("Santiago");
        usuarioActualizado.setRegion("Metropolitana");

        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        // When
        Usuario resultado = usuarioService.update(usuarioActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals(usuarioActualizado, resultado);
        assertEquals(1, resultado.getIdUsuario());
        assertEquals("dil_actualizado", resultado.getApodo());
        assertEquals("dilan insaggi", resultado.getNombre());
        verify(usuarioRepository, times(1)).existsById(1);
        verify(usuarioRepository, times(1)).save(usuarioActualizado);
    }

    @Test
    void testUpdate_usuarioNoExiste_devuelveNull() {
        // Given
        Usuario usuarioInexistente = new Usuario();
        usuarioInexistente.setIdUsuario(999);
        usuarioInexistente.setApodo("inexistente");
        usuarioInexistente.setNombre("Usuario");
        usuarioInexistente.setApellido("Inexistente");
        usuarioInexistente.setFechaNacimiento("2000-01-01");
        usuarioInexistente.setCorreo("inexistente@ejemplo.com");
        usuarioInexistente.setContrasena("123456");
        usuarioInexistente.setDireccion("Direccion Falsa");
        usuarioInexistente.setComuna("Comuna Falsa");
        usuarioInexistente.setCiudad("Ciudad Falsa");
        usuarioInexistente.setRegion("Region Falsa");

        when(usuarioRepository.existsById(999)).thenReturn(false);

        // When
        Usuario resultado = usuarioService.update(usuarioInexistente);

        // Then
        assertNull(resultado);
        verify(usuarioRepository, times(1)).existsById(999);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testUpdate_conUsuarioNulo() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            usuarioService.update(null);
        });
        verify(usuarioRepository, never()).existsById(anyInt());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    // TESTS PARA ELIMINAR USUARIO
    @Test
    void testDelete_eliminaUsuarioCorrectamente() {
        // Given
        doNothing().when(usuarioRepository).deleteById(1);

        // When
        assertDoesNotThrow(() -> {
            usuarioService.delete(1);
        });

        // Then
        verify(usuarioRepository, times(1)).deleteById(1);
    }

    @Test
    void testDelete_conIdNulo() {
        // Given
        doThrow(new IllegalArgumentException("ID no puede ser nulo")).when(usuarioRepository).deleteById(null);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.delete(null);
        });
        verify(usuarioRepository, times(1)).deleteById(null);
    }

    @Test
    void testDelete_conIdNoExistente() {
        // Given
        doNothing().when(usuarioRepository).deleteById(999);

        // When
        assertDoesNotThrow(() -> {
            usuarioService.delete(999);
        });

        // Then
        verify(usuarioRepository, times(1)).deleteById(999);
    }

    // TESTS ADICIONALES PARA COBERTURA COMPLETA
    @Test
    void testBuscarUsuarioPorId_conIdNulo() {
        // Given
        when(usuarioRepository.findById(null)).thenThrow(new IllegalArgumentException("ID no puede ser nulo"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.buscarUsuarioPorId(null);
        });
        verify(usuarioRepository, times(1)).findById(null);
    }

    @Test
    void testIntegracionCompleta_flujoCompleto() {
        // Given - Simular un flujo completo: save -> buscar -> update -> delete
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setApodo("testuser");
        nuevoUsuario.setNombre("Test");
        nuevoUsuario.setApellido("User");
        nuevoUsuario.setFechaNacimiento("1995-01-01");
        nuevoUsuario.setCorreo("test@ejemplo.com");
        nuevoUsuario.setContrasena("testpass");
        nuevoUsuario.setDireccion("Test Address");
        nuevoUsuario.setComuna("Test Comuna");
        nuevoUsuario.setCiudad("Test Ciudad");
        nuevoUsuario.setRegion("Test Region");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setIdUsuario(1);
        usuarioGuardado.setApodo("testuser");
        usuarioGuardado.setNombre("Test");
        usuarioGuardado.setApellido("User");
        usuarioGuardado.setFechaNacimiento("1995-01-01");
        usuarioGuardado.setCorreo("test@ejemplo.com");
        usuarioGuardado.setContrasena("testpass");
        usuarioGuardado.setDireccion("Test Address");
        usuarioGuardado.setComuna("Test Comuna");
        usuarioGuardado.setCiudad("Test Ciudad");
        usuarioGuardado.setRegion("Test Region");

        when(usuarioRepository.save(nuevoUsuario)).thenReturn(usuarioGuardado);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioGuardado));
        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.save(usuarioGuardado)).thenReturn(usuarioGuardado);
        doNothing().when(usuarioRepository).deleteById(1);

        // When & Then
        // 1. Guardar
        Usuario resultado1 = usuarioService.save(nuevoUsuario);
        assertNotNull(resultado1);
        assertEquals(1, resultado1.getIdUsuario());

        // 2. Buscar
        Usuario resultado2 = usuarioService.buscarUsuarioPorId(1);
        assertNotNull(resultado2);
        assertEquals(1, resultado2.getIdUsuario());

        // 3. Actualizar
        Usuario resultado3 = usuarioService.update(usuarioGuardado);
        assertNotNull(resultado3);
        assertEquals(1, resultado3.getIdUsuario());

        // 4. Eliminar
        assertDoesNotThrow(() -> usuarioService.delete(1));

        // Verificar todas las interacciones
        verify(usuarioRepository, times(2)).save(any(Usuario.class));
        verify(usuarioRepository, times(1)).findById(1);
        verify(usuarioRepository, times(1)).existsById(1);
        verify(usuarioRepository, times(1)).deleteById(1);
    }
}