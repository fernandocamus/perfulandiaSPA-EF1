package com.perfulandia.perfulandiaSPA_EF1.gestionCorreo.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.repository.CorreoRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CorreoRepositoryTest {

    @Mock
    private CorreoRepository correoRepository;

    @Test
    void buscarPorIdCorreo_retornaCorreoCorrecto() {
        Correo correo = new Correo();
        correo.setIdCorreo(1);
        correo.setDestinatario("mat.reyesa@duocuc.cl");
        correo.setAsunto("Prueba");
        correo.setCuerpo("Cuerpo del correo");
        correo.setArchivoAdjunto("archivo.pdf");

        when(correoRepository.buscarPorIdCorreo("1")).thenReturn(List.of(correo));

        List<Correo> resultado = correoRepository.buscarPorIdCorreo("1");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("mat.reyesa@duocuc.cl", resultado.get(0).getDestinatario());

        verify(correoRepository).buscarPorIdCorreo("1");
    }
}