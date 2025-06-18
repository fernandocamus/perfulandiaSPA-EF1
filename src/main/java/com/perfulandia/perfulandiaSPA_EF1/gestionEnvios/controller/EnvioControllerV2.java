package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.assemblers.EnvioModelAssembler;

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
@RequestMapping("/api/v2/gestionEnvios/envios")
@Tag(name = "Envíos V2", description = "Operaciones relacionadas con la gestión de envíos (Version 2 con HATEOAS)")
public class EnvioControllerV2 {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private EnvioModelAssembler envioAssembler;

    @Operation(summary = "Crear envío")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío creado correctamente")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Envio>> crearEnvio(@RequestBody Envio envio) {
        Envio envioGuardado = envioService.save(envio);
        return ResponseEntity.ok(envioAssembler.toModel(envioGuardado));
    }

    @Operation(summary = "Listar todos los envíos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay envios registrados")
    })
    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<Envio>>> ListarEnvios() {
        List<Envio> envios = envioService.buscarTodosEnvios();
        if (envios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Envio>> enviosModel = envios.stream()
                .map(envioAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(enviosModel,
                        linkTo(methodOn(EnvioControllerV2.class).ListarEnvios()).withSelfRel())
        );
    }

    @Operation(summary = "Buscar envío por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Envio>> buscarEnvioPorId(@PathVariable Integer id) {
        Envio envio = envioService.buscarEnvioPorId(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envioAssembler.toModel(envio));
    }

    @Operation(summary = "Actualizar envío")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @PutMapping
    public ResponseEntity<EntityModel<Envio>> actualizarEnvio(@RequestBody Envio envio) {
        Envio envioActualizado = envioService.update(envio);
        if (envioActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envioAssembler.toModel(envioActualizado));
    }

    @Operation(summary = "Eliminar envío por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Envío eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Integer id) {
        Envio envio = envioService.buscarEnvioPorId(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}