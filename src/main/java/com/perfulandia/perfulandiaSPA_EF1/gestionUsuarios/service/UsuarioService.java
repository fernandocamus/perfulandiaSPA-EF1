package com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.service;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //BUSCAR TODOS LOS USUARIOS Y POR ID
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).get();
    }

    //GUARDAR, ACTUALIZAR Y ELIMINAR USUARIO
    public Usuario save(Usuario usuario) { 
        return usuarioRepository.save(usuario);   
    }
    public Usuario update(Usuario usuario) {
        if (usuarioRepository.existsById(usuario.getIdUsuario())) {
            return usuarioRepository.save(usuario);
        } 
        return null;
    }
    public void delete(Integer idUsuario) { 
        usuarioRepository.deleteById(idUsuario);
    }
}