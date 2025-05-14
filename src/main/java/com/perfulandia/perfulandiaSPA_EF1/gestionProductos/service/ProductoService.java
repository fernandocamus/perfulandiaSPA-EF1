package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.service;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //BUSCAR TODOS LOS PRODUCTOS Y POR ID
    public List<Producto> buscarTodosProductos() {
        return productoRepository.findAll();
    }
    public Producto buscarProductoPorId(Integer id) {
        return productoRepository.findById(id).get();
    }

    //GUARDAR, ACTUALIZAR Y ELIMINAR PRODUCTO
    public Producto save(Producto producto) { 
        return productoRepository.save(producto);   
    }
    public Producto update(Producto producto) {
        if (productoRepository.existsById(producto.getIdProducto())) {
            return productoRepository.save(producto);
        } 
        return null;
    }
    public void delete(Integer idProducto) { 
        productoRepository.deleteById(idProducto);
    }
}