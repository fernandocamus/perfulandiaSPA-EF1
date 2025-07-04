package com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "correo")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Correo {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCorreo;

    @Column(nullable=false)
    private String destinatario;

    @Column(nullable=false)
    private String asunto; 

    @Column(nullable=false, length = 1000)
    private String cuerpo;

    @Column(nullable=true)
    private String archivoAdjunto;

    public void setId(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}