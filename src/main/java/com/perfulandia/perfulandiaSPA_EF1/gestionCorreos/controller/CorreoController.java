package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/gestionCorreos/correos") //URL base
public class CorreoController {

    @Autowired
    private CorreoService correoService;

    //GUARDAR Y ENVIAR CORREO
    @PostMapping 
    public ResponseEntity<String> guardarEnviar(@RequestBody Correo correo) {
        correoService.save(correo);
        correoService.enviarCorreo(correo);
        return ResponseEntity.ok("Correo enviado a: " + correo.getDestinatario());  
    }

    //BUSCAR CORREO Y BUSCAR CORREO POR ID
    @GetMapping()
    public ResponseEntity<List<Correo>> ListarCorreos() {
        List<Correo> correos = correoService.buscarTodosCorreos();
        if (correos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(correos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Correo> buscarCorreoPorId(@PathVariable Integer id) {
        Correo correo = correoService.buscarCorreoPorId(id);
        if (correo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(correo);
    }
    
    //ACTUALIZAR CORREO
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

