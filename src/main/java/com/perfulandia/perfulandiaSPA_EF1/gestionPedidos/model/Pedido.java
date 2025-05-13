package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model;

import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    //@Column(name = "idInventario", nullable=false)
    //private Inventario inventario;

    @Column(nullable=false)
    private double cantidadProductos;

    @Column(nullable=false)
    private double totalCompra;

    @Column(nullable=false)
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "idCorreo", nullable=false)
    private Correo correo;

    //@Column(name = "idUsuario", nullable=false)
    //private Usuario usuario;


}
