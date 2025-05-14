package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model.Envio;
import com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.repository.EnvioRepository;

import java.util.List;

@Service
@Transactional
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    //BUSCAR TODOS LOS ENVÍOS Y POR ID
    public List<Envio> buscarTodosEnvios() {
        return envioRepository.findAll();
    }
    public Envio buscarEnvioPorId(Integer id) {
        return envioRepository.findById(id).orElse(null);
    }

    //GUARDAR, ACTUALIZAR Y ELIMINAR ENVÍO
    public Envio save(Envio envio) { 
        return envioRepository.save(envio);   
    }
    public Envio update(Envio envio) {
        if (envioRepository.existsById(envio.getIdEnvio())) {
            return envioRepository.save(envio);
        } 
        return null;
    }
    public void delete(Integer idEnvio) { 
        envioRepository.deleteById(idEnvio);
    }
}
