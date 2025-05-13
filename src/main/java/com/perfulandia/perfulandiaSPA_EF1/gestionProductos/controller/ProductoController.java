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

import java.util.List;

@RestController
@RequestMapping("/api/gestionProductos/productos") //URL base
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    //BUSCAR PRODUCTOS
    @GetMapping()
    public ResponseEntity<List<Producto>> ListarProductos() {
        List<Producto> productos = productoService.buscarTodosProductos();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    //BUSCAR PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Integer id) {
        Producto producto = productoService.buscarProductoPorId(id);
        if (producto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    //GUARDAR PRODUCTO
    @PostMapping
    ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    //ACTUALIZAR PRODUCTO
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