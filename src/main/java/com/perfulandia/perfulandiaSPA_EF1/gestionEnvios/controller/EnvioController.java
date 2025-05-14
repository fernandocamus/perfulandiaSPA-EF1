package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service.EnvioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequestMapping("/api/gestionEnvios/envios") //URL base
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    //BUSCAR ENVÍO
    @GetMapping()
    public ResponseEntity<List<Envio>> ListarEnvios() {
        List<Envio> envios = envioService.buscarTodosEnvios();
        if (envios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(envios);
    }

    //BUSCAR ENVÍO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Envio> buscarEnvioPorId(@PathVariable Integer id) {
        Envio envio = envioService.buscarEnvioPorId(id);
        if (envio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(envio);
    }

    //GUARDAR ENVÍO
    @PostMapping
    ResponseEntity<Envio> guardarEnvio(@RequestBody Envio envio) {
        Envio nuevoEnvio = envioService.save(envio);
        return ResponseEntity.ok(nuevoEnvio);
    }

    //ACTUALIZAR ENVÍO
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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarEnvio(@PathVariable Integer id) {
        envioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
