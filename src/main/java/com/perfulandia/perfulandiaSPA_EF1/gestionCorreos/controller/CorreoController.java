package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.controller;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service.CorreoService; 
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  

import java.util.List;

@RestController
@RequestMapping("/api/V1/correos")
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

}
