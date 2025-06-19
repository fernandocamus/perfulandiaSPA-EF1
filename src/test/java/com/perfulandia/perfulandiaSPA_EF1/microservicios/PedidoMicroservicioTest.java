package com.perfulandia.perfulandiaSPA_EF1.microservicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.controller.PedidoController;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service.PedidoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
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

@WebMvcTest(PedidoController.class)
@ExtendWith(MockitoExtension.class)
public class PedidoMicroservicioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pedido pedidoTest;

    @BeforeEach
    void setUp() {
        Producto producto = new Producto();
        producto.setIdProducto(1);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Test Usuario");

        Correo correo = new Correo();
        correo.setIdCorreo(1);
        correo.setDestinatario("test@correo.com");

        pedidoTest = new Pedido();
        pedidoTest.setIdPedido(1);
        pedidoTest.setProducto(producto);
        pedidoTest.setUsuario(usuario);
        pedidoTest.setCorreo(correo);
        pedidoTest.setCantidadProductos(3);
        pedidoTest.setTotalCompra(15000);
        pedidoTest.setMetodoPago("TRANSFERENCIA");
    }

    @Test
    void testGuardarPedido_DeberiaCrearPedido() throws Exception {
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedidoTest);

        mockMvc.perform(post("/api/gestionPedidos/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1))
                .andExpect(jsonPath("$.metodoPago").value("TRANSFERENCIA"));

        verify(pedidoService).save(any(Pedido.class));
    }

    @Test
    void testListarPedidos_DeberiaRetornarLista() throws Exception {
        when(pedidoService.buscarTodosPedidos()).thenReturn(Arrays.asList(pedidoTest));

        mockMvc.perform(get("/api/gestionPedidos/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].idPedido").value(1));

        verify(pedidoService).buscarTodosPedidos();
    }

    @Test
    void testListarPedidos_SinResultados_DeberiaRetornarNoContent() throws Exception {
        when(pedidoService.buscarTodosPedidos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/gestionPedidos/pedidos"))
                .andExpect(status().isNoContent());

        verify(pedidoService).buscarTodosPedidos();
    }

    @Test
    void testBuscarPedidoPorId_Existe() throws Exception {
        when(pedidoService.buscarPedidoPorId(eq(1))).thenReturn(pedidoTest);

        mockMvc.perform(get("/api/gestionPedidos/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metodoPago").value("TRANSFERENCIA"));

        verify(pedidoService).buscarPedidoPorId(eq(1));
    }

    @Test
    void testBuscarPedidoPorId_NoExiste() throws Exception {
        when(pedidoService.buscarPedidoPorId(eq(999))).thenReturn(null);

        mockMvc.perform(get("/api/gestionPedidos/pedidos/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService).buscarPedidoPorId(eq(999));
    }

    @Test
    void testActualizarPedido_DeberiaActualizar() throws Exception {
        when(pedidoService.update(any(Pedido.class))).thenReturn(pedidoTest);

        mockMvc.perform(put("/api/gestionPedidos/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoTest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(1));

        verify(pedidoService).update(any(Pedido.class));
    }

    @Test
    void testActualizarPedido_NoExiste() throws Exception {
        when(pedidoService.update(any(Pedido.class))).thenReturn(null);

        mockMvc.perform(put("/api/gestionPedidos/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoTest)))
                .andExpect(status().isNotFound());

        verify(pedidoService).update(any(Pedido.class));
    }

    @Test
    void testEliminarPedido_DeberiaEliminar() throws Exception {
        when(pedidoService.buscarPedidoPorId(eq(1))).thenReturn(pedidoTest);
        doNothing().when(pedidoService).delete(eq(1));

        mockMvc.perform(delete("/api/gestionPedidos/pedidos/delete/1"))
                .andExpect(status().isOk());

        verify(pedidoService).buscarPedidoPorId(eq(1));
        verify(pedidoService).delete(eq(1));
    }

    @Test
    void testEliminarPedido_NoExiste() throws Exception {
        when(pedidoService.buscarPedidoPorId(eq(999))).thenReturn(null);

        mockMvc.perform(delete("/api/gestionPedidos/pedidos/delete/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService).buscarPedidoPorId(eq(999));
    }
}


