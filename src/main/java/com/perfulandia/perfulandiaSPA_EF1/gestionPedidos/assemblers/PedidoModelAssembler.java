package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.controller.PedidoControllerV2;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoControllerV2.class).buscarPedidoPorId(pedido.getIdPedido())).withSelfRel(),
                linkTo(methodOn(PedidoControllerV2.class).ListarPedidos()).withRel("pedidos"),
                linkTo(methodOn(PedidoControllerV2.class).actualizarPedido(pedido)).withRel("actualizar"),
                linkTo(methodOn(PedidoControllerV2.class).eliminarPedido(pedido.getIdPedido())).withRel("eliminar")
        );
    }
}