
package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service.ProductoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.assemblers.ProductoModelAssembler;

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
@RequestMapping("/api/v2/gestionProductos/productos")
@Tag(name = "Productos V2", description = "Operaciones relacionadas con los productos (Version 2 con HATEOAS)")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoAssembler;

    @Operation(summary = "Crear producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto guardado correctamente")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.ok(productoAssembler.toModel(nuevoProducto));
    }

    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> ListarProductos() {
        List<Producto> productos = productoService.buscarTodosProductos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(productoAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(productosModel,
                        linkTo(methodOn(ProductoControllerV2.class).ListarProductos()).withSelfRel())
        );
    }

    @Operation(summary = "Buscar producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> buscarProductoPorId(@PathVariable Integer id) {
        Producto producto = productoService.buscarProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoAssembler.toModel(producto));
    }

    @Operation(summary = "Actualizar producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping
    public ResponseEntity<EntityModel<Producto>> actualizarProducto(@RequestBody Producto producto) {
        Producto productoActualizado = productoService.update(producto);
        if (productoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoAssembler.toModel(productoActualizado));
    }

    @Operation(summary = "Eliminar producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id) {
        Producto producto = productoService.buscarProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}