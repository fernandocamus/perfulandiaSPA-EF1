package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller;

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

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/gestionCorreos/correos") //URL base
@Tag(name = "Correos", description = "Operaciones relacionadas con el envío y gestión correos")
public class CorreoController {

    @Autowired
    private CorreoService correoService;

    //CREAR Y ENVIAR CORREO
    @Operation(summary = "Crear y enviar correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo enviado correctamente")
    })
    @PostMapping 
    public ResponseEntity<String> guardarEnviar(@RequestBody Correo correo) {
        correoService.save(correo);
        correoService.enviarCorreo(correo);
        return ResponseEntity.ok("Correo enviado a: " + correo.getDestinatario());  
    }

    //LISTAR CORREOS
    @Operation(summary = "Listar todos los correos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No hay correos tegistrados")
    })
    @GetMapping()
    public ResponseEntity<List<Correo>> ListarCorreos() {
        List<Correo> correos = correoService.buscarTodosCorreos();
        if (correos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(correos);
    }

    //BUSCAR CORREO POR ID
    @Operation(summary = "Buscar correo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Correo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Correo> buscarCorreoPorId(@PathVariable Integer id) {
        Correo correo = correoService.buscarCorreoPorId(id);
        if (correo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(correo);
    }
    
    //ACTUALIZAR CORREO
    @Operation(summary = "Actualizar y reenviar correo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correo actualizado y reenviado correctamente"),
            @ApiResponse(responseCode = "404", description = "Correo no encontrado")
    })
    @PutMapping
    public ResponseEntity<String> actualizarCorreo(@RequestBody Correo correo) {
        Correo correoActualizado = correoService.update(correo);
        if (correoActualizado != null) {     
            correoService.enviarCorreo(correo);
            return ResponseEntity.ok("Correo actualizado y enviado a: " + correoActualizado.getDestinatario()); 
        }else{
            return ResponseEntity.notFound().build();   
        }
    }

    //ELIMINAR CORREO
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