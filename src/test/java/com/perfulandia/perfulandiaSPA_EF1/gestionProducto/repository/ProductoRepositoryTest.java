package com.perfulandia.perfulandiaSPA_EF1.gestionProducto.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.repository.ProductoRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductoRepositoryTest {

    @Mock
    private ProductoRepository productoRepository;

    @Test
    void buscarPorIdProducto_retornaProductoCorrecto() {
        MockitoAnnotations.openMocks(this);

        Producto producto = new Producto();
        producto.setIdProducto(1);
        producto.setNombre("Perfume");
        producto.setDescripcion("Aroma fresco");
        producto.setCategoria("Cosméticos");
        producto.setPrecio(15000);
        producto.setExistencias(10);

    }
}