package com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Producto {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @Column(nullable=false)
    private String nombre;

    @Column(nullable=false)
    private String descripcion; 

    @Column(nullable=false)
    private String categoria;

    @Column(nullable=true)
    private double precio;

    @Column(nullable=true)
    private double existencias;

}
