package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/gestionPedidos/pedidos") //URL base
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    //BUSCAR PEDIDO
    @GetMapping()
    public ResponseEntity<List<Pedido>> ListarPedidos() {
        List<Pedido> pedidos = pedidoService.buscarTodosPedidos();
        if (pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    //BUSCAR PEDIDO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }

    //GUARDAR PEDIDO
    @PostMapping
    ResponseEntity<Pedido> guardarPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    //ACTUALIZAR PEDIDO
    @PutMapping
    public ResponseEntity<Pedido> actualizarPedido(@RequestBody Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.update(pedido);
        if (pedidoActualizado != null) {
            return ResponseEntity.ok(pedidoActualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //ELIMINAR PEDIDO
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.delete(id);
        return ResponseEntity.ok().build();
    }
}