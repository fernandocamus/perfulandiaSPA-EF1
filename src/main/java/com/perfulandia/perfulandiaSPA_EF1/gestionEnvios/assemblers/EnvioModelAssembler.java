package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller.EnvioControllerV2;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EnvioModelAssembler implements RepresentationModelAssembler<Envio, EntityModel<Envio>> {

    @Override
    public EntityModel<Envio> toModel(Envio envio) {
        return EntityModel.of(envio,
                linkTo(methodOn(EnvioControllerV2.class).buscarEnvioPorId(envio.getIdEnvio())).withSelfRel(),
                linkTo(methodOn(EnvioControllerV2.class).ListarEnvios()).withRel("envios"),
                linkTo(methodOn(EnvioControllerV2.class).actualizarEnvio(envio)).withRel("actualizar"),
                linkTo(methodOn(EnvioControllerV2.class).eliminarEnvio(envio.getIdEnvio())).withRel("eliminar")
        );
    }
}