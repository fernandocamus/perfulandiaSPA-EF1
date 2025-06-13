package com.perfulandia.perfulandiaSPA_EF1.gestionProducto.service;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.repository.ProductoRepository;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceTest {

    @Mock
    ProductoRepository productoRepository;

    @InjectMocks
    ProductoService productoService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodosProductos() {
        when(productoRepository.findAll()).thenReturn(List.of(new Producto()));

        List<Producto> lista = productoService.buscarTodosProductos();

        assertFalse(lista.isEmpty());
    }

    @Test
    void testBuscarProductoPorId() {
        Producto p = new Producto();
        when(productoRepository.findById(1)).thenReturn(Optional.of(p));

        Producto resultado = productoService.buscarProductoPorId(1);

        assertNotNull(resultado);
    }

    @Test
    void testSave() {
        Producto p = new Producto();
        when(productoRepository.save(p)).thenReturn(p);

        Producto resultado = productoService.save(p);

        assertNotNull(resultado);
    }

    @Test
    void testUpdate_existente() {
        Producto p = new Producto();
        p.setIdProducto(1);

        when(productoRepository.existsById(1)).thenReturn(true);
        when(productoRepository.save(p)).thenReturn(p);

        Producto resultado = productoService.update(p);

        assertNotNull(resultado);
    }

    @Test
    void testUpdate_noExistente() {
        Producto p = new Producto();
        p.setIdProducto(2);

        when(productoRepository.existsById(2)).thenReturn(false);

        Producto resultado = productoService.update(p);

        assertNull(resultado);
    }

    @Test
    void testDelete() {
        doNothing().when(productoRepository).deleteById(1);

        productoService.delete(1);

        verify(productoRepository).deleteById(1);
    }
}