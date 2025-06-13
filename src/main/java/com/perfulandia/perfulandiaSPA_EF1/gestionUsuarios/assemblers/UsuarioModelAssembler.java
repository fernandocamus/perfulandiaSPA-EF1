package com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.controller.UsuarioControllerV2;
import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarioPorId(usuario.getIdUsuario())).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).ListarUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).actualizarUsuario(usuario)).withRel("actualizar"),
                linkTo(methodOn(UsuarioControllerV2.class).eliminarUsuario(usuario.getIdUsuario())).withRel("eliminar")
        );
    }
}