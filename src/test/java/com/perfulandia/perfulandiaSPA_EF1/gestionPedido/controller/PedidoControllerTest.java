package com.perfulandia.perfulandiaSPA_EF1.gestionPedido.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.controller.PedidoController;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service.PedidoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Test
    void listarPedidos_devuelve200() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setCantidadProductos(5);
        pedido.setTotalCompra(15000);
        pedido.setMetodoPago("Tarjeta");

        when(pedidoService.buscarTodosPedidos()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/gestionPedidos/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(1))
                .andExpect(jsonPath("$[0].cantidadProductos").value(5));
    }

    @Test
    void buscarPedidoPorId_devuelve200() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);
        pedido.setCantidadProductos(5);
        pedido.setTotalCompra(15000);
        pedido.setMetodoPago("Tarjeta");

        when(pedidoService.buscarPedidoPorId(1)).thenReturn(pedido);

        mockMvc.perform(get("/api/gestionPedidos/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1))
                .andExpect(jsonPath("$.totalCompra").value(15000));
    }
}