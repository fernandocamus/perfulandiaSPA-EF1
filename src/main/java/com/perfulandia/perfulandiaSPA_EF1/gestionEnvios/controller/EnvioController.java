package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/gestionEnvios/envios") //URL base
@Tag(name = "Envios", description = "Operaciones relacionadas con los envíos")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    //GUARDAR ENVÍO
    @Operation(summary = "Crear envío")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío guardado correctamente")
    })
    @PostMapping
    @SuppressWarnings("unused")
    ResponseEntity<Envio> guardarEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.save(envio);
        return ResponseEntity.ok(nuevoEnvio);
    }

    //LISTAR ENVÍOS
    @Operation(summary = "Listar todos los envíos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de envíos obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay envios registrados")
    })
    @GetMapping()
    public ResponseEntity<List<Envio>> ListarEnvios() {
        List<Envio> envios = envioService.buscarTodosEnvios();
        if (envios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    //BUSCAR ENVÍO POR ID
    @Operation(summary = "Buscar envío por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío encontrado"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarEnvioPorId(@PathVariable Integer id) {
        Envio envio = envioService.buscarEnvioPorId(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envio);
    }

    //ACTUALIZAR ENVÍO
    @Operation(summary = "Actualizar datos de envío")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @PutMapping
    public ResponseEntity<Envio> actualizarEnvio(@RequestBody Envio envio) {
        Envio envioActualizado = envioService.update(envio);
        if (envioActualizado != null) {
            return ResponseEntity.ok(envioActualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    //ELIMINAR ENVÍO
    @Operation(summary = "Eliminar envío por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Envío eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Envío no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Integer id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}