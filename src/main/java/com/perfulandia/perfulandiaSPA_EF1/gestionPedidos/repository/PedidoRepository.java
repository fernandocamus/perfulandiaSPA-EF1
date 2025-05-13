package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> { 

    @Query("SELECT p FROM Pedido p WHERE p.correo.idCorreo = :idCorreo")
    List<Pedido> buscarPorIdCorreo(@Param("idCorreo") Integer idCorreo);

}
