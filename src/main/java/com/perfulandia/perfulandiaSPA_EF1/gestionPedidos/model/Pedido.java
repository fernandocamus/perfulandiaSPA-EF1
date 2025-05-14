package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model;

import com.perfulandia.perfulandiaSPA_EF1.gestionUsuarios.model.Usuario;
import com.perfulandia.perfulandiaSPA_EF1.gestionCorreos.model.Correo;
import com.perfulandia.perfulandiaSPA_EF1.gestionProductos.model.Producto;
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

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable=false)
    private Producto producto;

    @Column(nullable=false)
    private double cantidadProductos;

    @Column(nullable=false)
    private double totalCompra;

    @Column(nullable=false)
    private String metodoPago;

    @ManyToOne
    @JoinColumn(name = "idCorreo", nullable=false)
    private Correo correo;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable=false)
    private Usuario usuario;


}
