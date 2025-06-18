
package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller.CorreoControllerV2;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CorreoModelAssembler implements RepresentationModelAssembler<Correo, EntityModel<Correo>> {

    @Override
    public EntityModel<Correo> toModel(Correo correo) {
        return EntityModel.of(correo,
                linkTo(methodOn(CorreoControllerV2.class).buscarCorreoPorId(correo.getIdCorreo())).withSelfRel(),
                linkTo(methodOn(CorreoControllerV2.class).ListarCorreos()).withRel("correos"),
                linkTo(methodOn(CorreoControllerV2.class).actualizarCorreo(correo)).withRel("actualizar"),
                linkTo(methodOn(CorreoControllerV2.class).eliminarCorreo(correo.getIdCorreo())).withRel("eliminar")
        );
    }
}