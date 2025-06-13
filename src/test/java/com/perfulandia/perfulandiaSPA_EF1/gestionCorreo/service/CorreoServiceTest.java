package com.perfulandia.perfulandiaSPA_EF1.gestionCorreo.service;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.repository.CorreoRepository;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CorreoServiceTest {

    @Mock
    private CorreoRepository correoRepository;

    @InjectMocks
    private CorreoService correoService;

    public CorreoServiceTest() {
        MockitoAnnotations.openMocks(this); 
    }

    @Test
    void testBuscarTodosCorreos() {

        Correo correo1 = new Correo();
        correo1.setDestinatario("mat.reyesa@duocuc.cl");

        Correo correo2 = new Correo();
        correo2.setDestinatario("fer.camusr@duocuc.cl");

        when(correoRepository.findAll()).thenReturn(Arrays.asList(correo1, correo2));

        List<Correo> resultado = correoService.buscarTodosCorreos();

        assertEquals(2, resultado.size());
        assertEquals("mat.reyesa@duocuc.cl", resultado.get(0).getDestinatario());
        verify(correoRepository, times(1)).findAll(); 
    }
}