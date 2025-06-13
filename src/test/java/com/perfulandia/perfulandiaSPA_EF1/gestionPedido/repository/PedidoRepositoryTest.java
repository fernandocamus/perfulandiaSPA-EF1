package com.perfulandia.perfulandiaSPA_EF1.gestionPedido.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.repository.*;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoRepositoryTest {

    @Test
    void testFindById() {
        PedidoRepository pedidoRepository = mock(PedidoRepository.class);

        // Crear un pedido falso
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setMetodoPago("Tarjeta");

        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        Optional<Pedido> resultado = pedidoRepository.findById(1);
        assertTrue(resultado.isPresent());
        assertEquals("Tarjeta", resultado.get().getMetodoPago());
        verify(pedidoRepository, times(1)).findById(1);
    }

    @Test
    void testSave() {
        PedidoRepository pedidoRepository = mock(PedidoRepository.class);

        Pedido pedido = new Pedido();
        pedido.setMetodoPago("Efectivo");

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoRepository.save(pedido);

        assertNotNull(resultado);
        assertEquals("Efectivo", resultado.getMetodoPago());

        verify(pedidoRepository, times(1)).save(pedido);
    }
}