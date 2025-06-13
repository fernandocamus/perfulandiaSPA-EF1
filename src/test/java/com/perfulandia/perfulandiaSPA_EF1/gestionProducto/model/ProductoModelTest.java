package com.perfulandia.perfulandiaSPA_EF1.gestionProducto.model;

import org.junit.jupiter.api.Test;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoModelTest {

    @Test
    void testGettersSetters() {
        Producto producto = new Producto();

        producto.setIdProducto(1);
        producto.setNombre("Perfume");
        producto.setDescripcion("Aroma floral");
        producto.setCategoria("Fragancias");
        producto.setPrecio(15000);
        producto.setExistencias(30);

        assertEquals(1, producto.getIdProducto());
        assertEquals("Perfume", producto.getNombre());
        assertEquals("Aroma floral", producto.getDescripcion());
        assertEquals("Fragancias", producto.getCategoria());
        assertEquals(15000, producto.getPrecio());
        assertEquals(30, producto.getExistencias());
    }
}