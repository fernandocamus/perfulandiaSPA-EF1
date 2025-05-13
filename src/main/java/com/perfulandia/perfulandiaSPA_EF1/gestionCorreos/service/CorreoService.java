package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.repository.CorreoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CorreoService {

    @Autowired
    private CorreoRepository correoRepository;

    //BUSCAR TODOS LOS CORREOS Y POR ID
    public List<Correo> buscarTodosCorreos() {
        return correoRepository.findAll();
    }
    public Correo buscarCorreoPorId(Integer id) {
        return correoRepository.findById(id).get();
    }

    //GUARDAR Y ELIMINAR CORREO
    public Correo save(Correo correo) { 
        return correoRepository.save(correo);   
    }
    public void delete(Integer idCorreo) { 
        correoRepository.deleteById(idCorreo);
    }
    
}
