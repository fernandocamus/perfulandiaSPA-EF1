package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Integer> {

    @Query("SELECT e FROM Envio e WHERE e.idEnvio = :idEnvio")
    List<Envio> buscarPorIdEnvio(@Param("idEnvio") String idEnvio);

}
