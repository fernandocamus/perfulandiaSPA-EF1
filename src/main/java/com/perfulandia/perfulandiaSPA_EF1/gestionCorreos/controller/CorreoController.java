package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService; 
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/gestionCorreos/correos") //URL base
public class CorreoController {

    @Autowired
    private CorreoService correoService;

    @GetMapping() //puede ser ("/todos")
    public ResponseEntity<List<Correo>> ListarCorreos() {
        List<Correo> correos = correoService.buscarTodosCorreos();
        if (correos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(correos);
    }


    @PostMapping 
    ResponseEntity<Correo> guardarCorreo(@RequestBody Correo correo) {
        Correo nuevoCorreo = correoService.save(correo);
        return ResponseEntity.ok(nuevoCorreo);
    }
}