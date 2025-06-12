package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service.ProductoService; 

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
@RequestMapping("/api/gestionProductos/productos") //URL base
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    //GUARDAR PRODUCTO
    @Operation(summary = "Crear producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto guardado correctamente")
    })
    @PostMapping
    ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    //LISTAR PRODUCTOS
    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    @GetMapping()
    public ResponseEntity<List<Producto>> ListarProductos() {
        List<Producto> productos = productoService.buscarTodosProductos();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    //BUSCAR PRODUCTO POR ID
    @Operation(summary = "Buscar producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Integer id) {
        Producto producto = productoService.buscarProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    //ACTUALIZAR PRODUCTO
    @Operation(summary = "Actualizar producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping
    public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto) {
        Producto productoActualizado = productoService.update(producto);
        if (productoActualizado != null) {
            return ResponseEntity.ok(productoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //ELIMINAR PRODUCTO
    @Operation(summary = "Eliminar producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "204", description = "Producto no encontrado")
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