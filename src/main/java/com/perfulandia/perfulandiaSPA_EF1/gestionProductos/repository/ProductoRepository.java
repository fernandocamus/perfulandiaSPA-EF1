package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    List<Producto> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria")
    List<Producto> buscarPorCategoria(@Param("categoria") String categoria);

    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN :minPrecio AND :maxPrecio")
    List<Producto> buscarPorRangoPrecio(@Param("minPrecio") double minPrecio, @Param("maxPrecio") double maxPrecio);

}
