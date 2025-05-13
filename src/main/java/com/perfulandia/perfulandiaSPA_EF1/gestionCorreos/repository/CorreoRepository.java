package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorreoRepository extends JpaRepository<Correo, Integer> { // Cambia el tipo de ID según tu entidad

    // Aqui proporcionaremos métodos CRUD y consultas personalizadas
    @Query("SELECT c FROM Correo c WHERE c.destinatario = :destinatario")
    List<Correo> buscarPorDestinatario(@Param("destinatario")String destinatario);

}