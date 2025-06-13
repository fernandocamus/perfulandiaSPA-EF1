package com.perfulandia.perfulandiaSPA_EF1.gestionPedido.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.repository.PedidoRepository;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service.PedidoService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    public PedidoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_conDatosValidos_retornaPedido() {
        Pedido pedido = new Pedido();
        pedido.setMetodoPago("Tarjeta");

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoService.save(pedido);

        assertNotNull(resultado);
        assertEquals("Tarjeta", resultado.getMetodoPago());
        verify(pedidoRepository, times(1)).save(pedido);
    }

    @Test
    void delete_conIdExistente_ejecutaDelete() {
        doNothing().when(pedidoRepository).deleteById(5);

        pedidoService.delete(5);

        verify(pedidoRepository, times(1)).deleteById(5);
    }
}