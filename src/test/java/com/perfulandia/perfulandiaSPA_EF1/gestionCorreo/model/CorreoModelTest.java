package com.perfulandia.perfulandiaSPA_EF1.gestionCorreo.model;

import org.junit.jupiter.api.Test;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;

public class CorreoModelTest{
    @Test
    void testCrearCorreoYVerificarLosDatos(){
        Correo correo = new Correo();

        correo.setIdCorreo(1);
        correo.setDestinatario("mat.reyesa@duocuc.cl");
        correo.setAsunto("Prueba Correo");
        correo.setCuerpo("Envio correo de prueba");
        correo.setArchivoAdjunto("correoPrueba.pdf");
    }
}
