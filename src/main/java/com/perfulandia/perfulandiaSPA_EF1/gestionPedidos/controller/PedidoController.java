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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/gestionPedidos/pedidos") //URL base
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    //GUARDAR PEDIDO
    @Operation(summary = "Crear pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido guardado correctamente")
    })
    @PostMapping
    ResponseEntity<Pedido> guardarPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    //LISTAR PEDIDOS
    @Operation(summary = "Listar todos los pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenidas correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay pedidos registrados")
    })
    @GetMapping()
    public ResponseEntity<List<Pedido>> ListarPedidos() {
        List<Pedido> pedidos = pedidoService.buscarTodosPedidos();
        if (pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    //BUSCAR PEDIDO POR ID
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedido);
    }

    //ACTUALIZAR PEDIDO
    @Operation(summary = "Actualizar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
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
    @Operation(summary = "Eliminar pedido por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
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