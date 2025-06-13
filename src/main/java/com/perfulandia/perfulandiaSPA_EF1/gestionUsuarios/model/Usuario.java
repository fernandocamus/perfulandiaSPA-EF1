package com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {
    
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable=false)
    private String apodo;

    @Column(nullable=false)
    private String nombre; 

    @Column(nullable=false)
    private String apellido;

    @Column(nullable=false)
    private String fechaNacimiento;

    @Column(nullable=false)
    private String correo;

    @Column(nullable=false)
    private String contrasena;

    @Column(nullable=false)
    private String direccion;

    @Column(nullable=false)
    private String comuna;

    @Column(nullable=false)
    private String ciudad;

    @Column(nullable=false)
    private String region;

    public void setId(int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

}