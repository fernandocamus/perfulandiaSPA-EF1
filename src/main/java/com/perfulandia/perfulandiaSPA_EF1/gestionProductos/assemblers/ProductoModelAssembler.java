package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.controller.ProductoControllerV2;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoControllerV2.class).buscarProductoPorId(producto.getIdProducto())).withSelfRel(),
                linkTo(methodOn(ProductoControllerV2.class).ListarProductos()).withRel("productos"),
                linkTo(methodOn(ProductoControllerV2.class).actualizarProducto(producto)).withRel("actualizar"),
                linkTo(methodOn(ProductoControllerV2.class).eliminarProducto(producto.getIdProducto())).withRel("eliminar")
        );
    }
}