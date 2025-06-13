package com.perfulandia.perfulandiaSPA_EF1.gestionProducto.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.controller.ProductoController;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void listarProductos_devuelve200() throws Exception {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("Perfume");
        producto.setDescripcion("Descripcion de prueba");
        producto.setCategoria("Categoria1");
        producto.setPrecio(10000);
        producto.setExistencias(50);

        when(productoService.buscarTodosProductos()).thenReturn(List.of(producto));

        mockMvc.perform(get("/api/gestionProductos/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Perfume"));
    }
}