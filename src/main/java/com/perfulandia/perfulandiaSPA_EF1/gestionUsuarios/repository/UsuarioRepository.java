package com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.repository;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario")
    List<Usuario> buscarPorIdUsuario(@Param("idUsuario") Integer idUsuario);

}