package com.perfulandia.perfulandiaSPA_EF1.gestionEnvios.model;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "envio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envio {
    
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable=false)
    private Usuario usuario;

    @Column(nullable=false)
    private String fechaEnvio;

    @Column(nullable=false)
    private String estadoEnvio;

    @Column(nullable=false)
    private double costoEnvio;

    @Column(nullable=false)
    private String fechaEntregaEstimada;

    @Column(nullable=false)
    private String fechaEntregaFinal;
}
