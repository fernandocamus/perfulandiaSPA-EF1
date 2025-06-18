package com.perfulandia.perfulandiaSPA_EF1.gestionProducto.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service.ProductoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.assemblers.ProductoModelAssembler;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.controller.ProductoControllerV2;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductoControllerV2.class)
public class ProductoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @MockBean
    private ProductoModelAssembler productoAssembler;

    @Test
    void testListarProductos_devuelveStatus200() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        EntityModel<Producto> productoModel = EntityModel.of(producto);

        when(productoService.buscarTodosProductos()).thenReturn(List.of(producto));
        when(productoAssembler.toModel(any(Producto.class))).thenReturn(productoModel);

        mockMvc.perform(get("/api/v2/gestionProductos/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productoList[0].idProducto").value(1)); // Ajusta según el JSON
    }

    @Test
    void testListarProductos_listaVacia_devuelveStatus204() throws Exception {
        when(productoService.buscarTodosProductos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/gestionProductos/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarProductoPorId_devuelveStatus200() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        EntityModel<Producto> productoModel = EntityModel.of(producto);

        when(productoService.buscarProductoPorId(1)).thenReturn(producto);
        when(productoAssembler.toModel(any(Producto.class))).thenReturn(productoModel);

        mockMvc.perform(get("/api/v2/gestionProductos/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    void testBuscarProductoPorId_noExiste_devuelveStatus404() throws Exception {
        when(productoService.buscarProductoPorId(999)).thenReturn(null);

        mockMvc.perform(get("/api/v2/gestionProductos/productos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarProductoPorId_formatoInvalido_devuelveStatus400() throws Exception {
        mockMvc.perform(get("/api/v2/gestionProductos/productos/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEliminarProducto_devuelveStatus204() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        when(productoService.buscarProductoPorId(1)).thenReturn(producto);

        mockMvc.perform(delete("/api/v2/gestionProductos/productos/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarProducto_noExiste_devuelveStatus404() throws Exception {
        when(productoService.buscarProductoPorId(999)).thenReturn(null);

        mockMvc.perform(delete("/api/v2/gestionProductos/productos/delete/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGuardarProducto_devuelveStatus200() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        EntityModel<Producto> productoModel = EntityModel.of(producto);

        when(productoService.save(any(Producto.class))).thenReturn(producto);
        when(productoAssembler.toModel(any(Producto.class))).thenReturn(productoModel);

        String jsonProducto = "{\"idProducto\":1}";

        mockMvc.perform(post("/api/v2/gestionProductos/productos")
                .contentType("application/json")
                .content(jsonProducto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    void testActualizarProducto_devuelveStatus200() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        EntityModel<Producto> productoModel = EntityModel.of(producto);

        when(productoService.update(any(Producto.class))).thenReturn(producto);
        when(productoAssembler.toModel(any(Producto.class))).thenReturn(productoModel);

        String jsonProducto = "{\"idProducto\":1}";

        mockMvc.perform(put("/api/v2/gestionProductos/productos")
                .contentType("application/json")
                .content(jsonProducto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    void testActualizarProducto_noExiste_devuelveStatus404() throws Exception {
        when(productoService.update(any(Producto.class))).thenReturn(null);

        String jsonProducto = "{\"idProducto\":999}";

        mockMvc.perform(put("/api/v2/gestionProductos/productos")
                .contentType("application/json")
                .content(jsonProducto))
                .andExpect(status().isNotFound());
    }
}
