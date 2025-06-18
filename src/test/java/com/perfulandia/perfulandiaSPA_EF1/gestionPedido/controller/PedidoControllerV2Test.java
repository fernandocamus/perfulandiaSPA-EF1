package com.perfulandia.perfulandiaSPA_EF1.gestionPedido.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.controller.PedidoControllerV2;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service.PedidoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.assemblers.PedidoModelAssembler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PedidoControllerV2.class)
public class PedidoControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @MockBean
    private PedidoModelAssembler pedidoAssembler;

    @Test
    void testListarPedidos_devuelveStatus200() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);

        EntityModel<Pedido> pedidoModel = EntityModel.of(pedido);

        when(pedidoService.buscarTodosPedidos()).thenReturn(List.of(pedido));
        when(pedidoAssembler.toModel(any(Pedido.class))).thenReturn(pedidoModel);

        mockMvc.perform(get("/api/v2/gestionPedidos/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pedidoList[0].idPedido").value(1)); 
    }

    @Test
    void testListarPedidos_listaVacia_devuelveStatus204() throws Exception {
        when(pedidoService.buscarTodosPedidos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v2/gestionPedidos/pedidos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarPedidoPorId_devuelveStatus200() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1);

        EntityModel<Pedido> pedidoModel = EntityModel.of(pedido);

        when(pedidoService.buscarPedidoPorId(1)).thenReturn(pedido);
        when(pedidoAssembler.toModel(any(Pedido.class))).thenReturn(pedidoModel);

        mockMvc.perform(get("/api/v2/gestionPedidos/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));
    }

    @Test
    void testBuscarPedidoPorId_pedidoNoExiste_devuelveStatus404() throws Exception {
        when(pedidoService.buscarPedidoPorId(9999)).thenReturn(null);

        mockMvc.perform(get("/api/v2/gestionPedidos/pedidos/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarPedidoPorId_formatoInvalido_devuelveStatus400() throws Exception {
        mockMvc.perform(get("/api/v2/gestionPedidos/pedidos/abc"))
                .andExpect(status().isBadRequest());
    }
}
