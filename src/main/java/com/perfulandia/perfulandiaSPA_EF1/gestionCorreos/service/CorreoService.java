package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.service;

// Imports basicos
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.repository.CorreoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Imports para el envio de correos
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;

// Imports de libreria
import java.util.List;

@Service
@Transactional
public class CorreoService {

    @Autowired
    private CorreoRepository correoRepository;

    @Autowired
    private JavaMailSender mailSender;

    //BUSCAR TODOS LOS CORREOS Y POR ID
    public List<Correo> buscarTodosCorreos() {
        return correoRepository.findAll();
    }
    public Correo buscarCorreoPorId(Integer id) {
        return correoRepository.findById(id).get();
    }

    //GUARDAR, ACTUALIZAR Y ELIMINAR CORREO
    public Correo save(Correo correo) { 
        return correoRepository.save(correo);   
    }
    public Correo update(Correo correo) {
        if (correoRepository.existsById(correo.getIdCorreo())) {
            return correoRepository.save(correo);
        } 
        return null;
    }
    public void delete(Integer idCorreo) { 
        correoRepository.deleteById(idCorreo);
    }

    // ENVIAR CORREO
    public void enviarCorreo(Correo correo) {
        try {
            MimeMessage correoFormato = mailSender.createMimeMessage();
            MimeMessageHelper msg = new MimeMessageHelper(correoFormato, true);

            // Rellenar el correo
            msg.setTo(correo.getDestinatario());
            msg.setSubject(correo.getAsunto());
            msg.setText(correo.getCuerpo(), true);

            // Para adjuntar factura
            File archivo = new File(correo.getArchivoAdjunto());
            msg.addAttachment(archivo.getName(), archivo);

            mailSender.send(correoFormato);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}