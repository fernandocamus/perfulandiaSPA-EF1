package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.assemblers.CorreoModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/gestionCorreos/correos")
@Tag(name = "Correos V2", description = "Operaciones relacionadas con el envío y gestión correos (Version 2 con HATEOAS)")
public class CorreoControllerV2 {

    @Autowired
    private CorreoService correoService;

    @Autowired
    private CorreoModelAssembler correoAssembler;

    @Operation(summary = "Crear y enviar correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo enviado correctamente")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Correo>> guardarEnviar(@RequestBody Correo correo) {
        correoService.save(correo);
        correoService.enviarCorreo(correo);
        return ResponseEntity.ok(correoAssembler.toModel(correo));
    }

    @Operation(summary = "Listar todos los correos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de correos obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay correos registrados")
    })
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<Correo>>> ListarCorreos() {
        List<Correo> correos = correoService.buscarTodosCorreos();
        if (correos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Correo>> correosModel = correos.stream()
                .map(correoAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(correosModel,
                        linkTo(methodOn(CorreoControllerV2.class).ListarCorreos()).withSelfRel())
        );
    }

    @Operation(summary = "Buscar correo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Correo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Correo>> buscarCorreoPorId(@PathVariable Integer id) {
        Correo correo = correoService.buscarCorreoPorId(id);
        if (correo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(correoAssembler.toModel(correo));
    }

    @Operation(summary = "Actualizar y reenviar correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo actualizado y reenviado correctamente"),
            @ApiResponse(responseCode = "404", description = "Correo no encontrado")
    })
    @PutMapping
    public ResponseEntity<EntityModel<Correo>> actualizarCorreo(@RequestBody Correo correo) {
        Correo correoActualizado = correoService.update(correo);
        if (correoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        correoService.enviarCorreo(correoActualizado);
        return ResponseEntity.ok(correoAssembler.toModel(correoActualizado));
    }

    @Operation(summary = "Eliminar correo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Correo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Correo no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarCorreo(@PathVariable Integer id) {
        Correo correo = correoService.buscarCorreoPorId(id);
        if (correo == null) {
            return ResponseEntity.notFound().build();
        }
        correoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}