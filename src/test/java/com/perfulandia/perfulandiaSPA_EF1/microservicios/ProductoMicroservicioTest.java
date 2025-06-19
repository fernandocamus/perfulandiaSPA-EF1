package com.perfulandia.perfulandiaSPA_EF1.microservicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.controller.ProductoController;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoMicroservicioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Producto productoTest;

    @BeforeEach
    void setUp() {
        productoTest = new Producto();
        productoTest.setIdProducto(1);
        productoTest.setNombre("Perfume");
        productoTest.setDescripcion("Aroma cítrico");
        productoTest.setCategoria("Fragancia");
        productoTest.setPrecio(19990);
        productoTest.setExistencias(50);
    }

    @Test
    void testGuardarProducto() throws Exception {
        when(productoService.save(any(Producto.class))).thenReturn(productoTest);

        mockMvc.perform(post("/api/gestionProductos/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfume"));

        verify(productoService).save(any(Producto.class));
    }

    @Test
    void testListarProductos_ConDatos() throws Exception {
        when(productoService.buscarTodosProductos()).thenReturn(Arrays.asList(productoTest));

        mockMvc.perform(get("/api/gestionProductos/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(productoService).buscarTodosProductos();
    }

    @Test
    void testListarProductos_SinDatos() throws Exception {
        when(productoService.buscarTodosProductos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/gestionProductos/productos"))
                .andExpect(status().isNoContent());

        verify(productoService).buscarTodosProductos();
    }

    @Test
    void testBuscarProductoPorId_Existe() throws Exception {
        when(productoService.buscarProductoPorId(eq(1))).thenReturn(productoTest);

        mockMvc.perform(get("/api/gestionProductos/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfume"));

        verify(productoService).buscarProductoPorId(1);
    }

    @Test
    void testBuscarProductoPorId_NoExiste() throws Exception {
        when(productoService.buscarProductoPorId(eq(999))).thenReturn(null);

        mockMvc.perform(get("/api/gestionProductos/productos/999"))
                .andExpect(status().isNotFound());

        verify(productoService).buscarProductoPorId(999);
    }

    @Test
    void testActualizarProducto_Existe() throws Exception {
        when(productoService.update(any(Producto.class))).thenReturn(productoTest);

        mockMvc.perform(put("/api/gestionProductos/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfume"));

        verify(productoService).update(any(Producto.class));
    }

    @Test
    void testActualizarProducto_NoExiste() throws Exception {
        when(productoService.update(any(Producto.class))).thenReturn(null);

        mockMvc.perform(put("/api/gestionProductos/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productoTest)))
                .andExpect(status().isNotFound());

        verify(productoService).update(any(Producto.class));
    }

    @Test
    void testEliminarProducto_Existe() throws Exception {
        when(productoService.buscarProductoPorId(eq(1))).thenReturn(productoTest);
        doNothing().when(productoService).delete(1);

        mockMvc.perform(delete("/api/gestionProductos/productos/delete/1"))
                .andExpect(status().isNoContent());

        verify(productoService).buscarProductoPorId(1);
        verify(productoService).delete(1);
    }

    @Test
    void testEliminarProducto_NoExiste() throws Exception {
        when(productoService.buscarProductoPorId(eq(999))).thenReturn(null);

        mockMvc.perform(delete("/api/gestionProductos/productos/delete/999"))
                .andExpect(status().isNotFound());

        verify(productoService).buscarProductoPorId(999);
    }
}
