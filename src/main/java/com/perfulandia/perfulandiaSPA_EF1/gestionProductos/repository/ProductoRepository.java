package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.idProducto = :idProducto")
    List<Producto> buscarPorIdProducto(@Param("idProducto") String idProducto);

}