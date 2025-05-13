package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService; 
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/api/gestionCorreos/correos") //URL base
public class CorreoController {

    @Autowired
    private CorreoService correoService;

    //BUSCAR CORREO
    @GetMapping()
    public ResponseEntity<List<Correo>> ListarCorreos() {
        List<Correo> correos = correoService.buscarTodosCorreos();
        if (correos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(correos);
    }

    //BUSCAR CORREO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Correo> buscarCorreoPorId(@PathVariable Integer id) {
        Correo correo = correoService.buscarCorreoPorId(id);
        if (correo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(correo);
    }
    
    //GUARDAR CORREO
    @PostMapping 
    ResponseEntity<Correo> guardarCorreo(@RequestBody Correo correo) {
        Correo nuevoCorreo = correoService.save(correo);
        return ResponseEntity.ok(nuevoCorreo);
    }

    //ACTUALIZAR CORREO
    @PutMapping
    public ResponseEntity<Correo> actualizarCorreo(@RequestBody Correo correo) {
        Correo correoActualizado = correoService.update(correo);
        if (correoActualizado != null) {
            return ResponseEntity.ok(correoActualizado);
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