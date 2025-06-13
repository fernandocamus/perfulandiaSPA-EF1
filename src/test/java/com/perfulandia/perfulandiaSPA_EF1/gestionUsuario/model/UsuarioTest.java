package com.perfulandia.perfulandiaSPA_EF1.gestionUsuario.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
    }

    @Test
    void testConstructorVacio() {
        Usuario usuarioVacio = new Usuario();
        assertNotNull(usuarioVacio);
        assertNull(usuarioVacio.getIdUsuario());
        assertNull(usuarioVacio.getApodo());
        assertNull(usuarioVacio.getNombre());
        assertNull(usuarioVacio.getApellido());
        assertNull(usuarioVacio.getFechaNacimiento());
        assertNull(usuarioVacio.getCorreo());
        assertNull(usuarioVacio.getContrasena());
        assertNull(usuarioVacio.getDireccion());
        assertNull(usuarioVacio.getComuna());
        assertNull(usuarioVacio.getCiudad());
        assertNull(usuarioVacio.getRegion());
    }

    @Test
    void testConstructorConTodosLosParametros() {
        Usuario usuarioCompleto = new Usuario(
            1,
            "dil",
            "dilan",
            "fuentes",
            "1990-02-02",
            "dilan.dilan@gmail.com",
            "123456",
            "Calle 123",
            "Las Condes",
            "Santiago",
            "Metropolitana"
        );

        assertNotNull(usuarioCompleto);
        assertEquals(1, usuarioCompleto.getIdUsuario());
        assertEquals("dil", usuarioCompleto.getApodo());
        assertEquals("dilan", usuarioCompleto.getNombre());
        assertEquals("fuentes", usuarioCompleto.getApellido());
        assertEquals("1990-02-02", usuarioCompleto.getFechaNacimiento());
        assertEquals("dilan.dilan@gmail.com", usuarioCompleto.getCorreo());
        assertEquals("123456", usuarioCompleto.getContrasena());
        assertEquals("Calle 123", usuarioCompleto.getDireccion());
        assertEquals("Las Condes", usuarioCompleto.getComuna());
        assertEquals("Santiago", usuarioCompleto.getCiudad());
        assertEquals("Metropolitana", usuarioCompleto.getRegion());
    }

    @Test
    void testSettersYGettersIdUsuario() {
        usuario.setIdUsuario(1);
        assertEquals(1, usuario.getIdUsuario());
    }

    @Test
    void testSettersYGettersApodo() {
        usuario.setApodo("dil");
        assertEquals("dil", usuario.getApodo());
    }

    @Test
    void testSettersYGettersNombre() {
        usuario.setNombre("dilan");
        assertEquals("dilan", usuario.getNombre());
    }

    @Test
    void testSettersYGettersApellido() {
        usuario.setApellido("fuentes");
        assertEquals("fuentes", usuario.getApellido());
    }

    @Test
    void testSettersYGettersFechaNacimiento() {
        usuario.setFechaNacimiento("1990-02-02");
        assertEquals("1990-02-02", usuario.getFechaNacimiento());
    }

    @Test
    void testSettersYGettersCorreo() {
        usuario.setCorreo("dilan.dilan@gmail.com");
        assertEquals("dilan.dilan@gmail.com", usuario.getCorreo());
    }

    @Test
    void testSettersYGettersContrasena() {
        usuario.setContrasena("123456");
        assertEquals("123456", usuario.getContrasena());
    }

    @Test
    void testSettersYGettersDireccion() {
        usuario.setDireccion("Calle 123");
        assertEquals("Calle 123", usuario.getDireccion());
    }

    @Test
    void testSettersYGettersComuna() {
        usuario.setComuna("Las Condes");
        assertEquals("Las Condes", usuario.getComuna());
    }

    @Test
    void testSettersYGettersCiudad() {
        usuario.setCiudad("Santiago");
        assertEquals("Santiago", usuario.getCiudad());
    }

    @Test
    void testSettersYGettersRegion() {
        usuario.setRegion("Metropolitana");
        assertEquals("Metropolitana", usuario.getRegion());
    }

    @Test
    void testEqualsYHashCode() {
        Usuario usuario1 = new Usuario(
            1,
            "dil",
            "dilan",
            "fuentes",
            "1990-02-02",
            "dilan.dilan@gmail.com",
            "123456",
            "Calle 123",
            "Las Condes",
            "Santiago",
            "Metropolitana"
        );

        Usuario usuario2 = new Usuario(
            1,
            "dil",
            "dilan",
            "fuentes",
            "1990-02-02",
            "dilan.dilan@gmail.com",
            "123456",
            "Calle 123",
            "Las Condes",
            "Santiago",
            "Metropolitana"
        );

        Usuario usuario3 = new Usuario(
            2,
            "fer",
            "fernando",
            "camuz",
            "1985-10-10",
            "camus@gmail.com",
            "654321",
            "Avenida 456",
            "Providencia",
            "Santiago",
            "Metropolitana"
        );

        assertEquals(usuario1, usuario2);
        assertNotEquals(usuario1, usuario3);
        assertNotEquals(usuario1, null);
        assertEquals(usuario1, usuario1); // reflexivo

        assertEquals(usuario1.hashCode(), usuario2.hashCode());
        assertNotEquals(usuario1.hashCode(), usuario3.hashCode());
    }

    @Test
    void testToString() {
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

        String resultado = usuario.toString();
        
        assertNotNull(resultado);
        assertTrue(resultado.contains("Usuario"));
        assertTrue(resultado.contains("idUsuario=1"));
        assertTrue(resultado.contains("apodo=dil"));
        assertTrue(resultado.contains("nombre=dilan"));
        assertTrue(resultado.contains("correo=dilan.dilan@gmail.com"));
    }

    @Test
    void testSetIdLanzaExcepcion() {
        assertThrows(UnsupportedOperationException.class, () -> {
            usuario.setId(1);
        });
    }

    @Test
    void testValidacionCamposObligatorios() {
        assertDoesNotThrow(() -> {
            usuario.setApodo("test");
            usuario.setNombre("Test");
            usuario.setApellido("User");
            usuario.setFechaNacimiento("2000-01-01");
            usuario.setCorreo("test@ejemplo.com");
            usuario.setContrasena("password");
            usuario.setDireccion("Test Address");
            usuario.setComuna("Test Comuna");
            usuario.setCiudad("Test Ciudad");
            usuario.setRegion("Test Region");
        });

        assertEquals("test", usuario.getApodo());
        assertEquals("Test", usuario.getNombre());
        assertEquals("User", usuario.getApellido());
        assertEquals("2000-01-01", usuario.getFechaNacimiento());
        assertEquals("test@ejemplo.com", usuario.getCorreo());
        assertEquals("password", usuario.getContrasena());
        assertEquals("Test Address", usuario.getDireccion());
        assertEquals("Test Comuna", usuario.getComuna());
        assertEquals("Test Ciudad", usuario.getCiudad());
        assertEquals("Test Region", usuario.getRegion());
    }
}