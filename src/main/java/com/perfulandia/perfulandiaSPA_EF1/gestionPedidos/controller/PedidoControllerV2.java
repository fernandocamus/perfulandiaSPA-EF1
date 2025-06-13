package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service.PedidoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.assemblers.PedidoModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/gestionPedidos/pedidos")
@Tag(name = "Pedidos V2", description = "Operaciones relacionadas con los pedidos (Version 2 con HATEOAS)")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler pedidoAssembler;

    @Operation(summary = "Crear pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido guardado correctamente")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> guardarPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido);
        return ResponseEntity.ok(pedidoAssembler.toModel(nuevoPedido));
    }

    @Operation(summary = "Listar todos los pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenidas correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay pedidos registrados")
    })
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> ListarPedidos() {
        List<Pedido> pedidos = pedidoService.buscarTodosPedidos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Pedido>> pedidosModel = pedidos.stream()
                .map(pedidoAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(pedidosModel,
                        linkTo(methodOn(PedidoControllerV2.class).ListarPedidos()).withSelfRel())
        );
    }

    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> buscarPedidoPorId(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidoAssembler.toModel(pedido));
    }

    @Operation(summary = "Actualizar pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping
    public ResponseEntity<EntityModel<Pedido>> actualizarPedido(@RequestBody Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.update(pedido);
        if (pedidoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidoAssembler.toModel(pedidoActualizado));
    }

    @Operation(summary = "Eliminar pedido por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}