package com.perfulandia.perfulandiaSPA_EF1.gestionPedido.model;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoModelTest {

    @Test
    void testGettersAndSetters() {
        Pedido pedido = new Pedido();

        pedido.setIdPedido(10);

        Producto producto = new Producto();
        producto.setIdProducto(1);
        pedido.setProducto(producto);

        pedido.setCantidadProductos(3.5);
        pedido.setTotalCompra(12000.0);
        pedido.setMetodoPago("Efectivo");

        Correo correo = new Correo();
        correo.setIdCorreo(5);
        pedido.setCorreo(correo);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(7);
        pedido.setUsuario(usuario);

        // Asserts
        assertEquals(10, pedido.getIdPedido());
        assertEquals(1, pedido.getProducto().getIdProducto());
        assertEquals(3.5, pedido.getCantidadProductos());
        assertEquals(12000.0, pedido.getTotalCompra());
        assertEquals("Efectivo", pedido.getMetodoPago());
        assertEquals(5, pedido.getCorreo().getIdCorreo());
        assertEquals(7, pedido.getUsuario().getIdUsuario());
    }
}