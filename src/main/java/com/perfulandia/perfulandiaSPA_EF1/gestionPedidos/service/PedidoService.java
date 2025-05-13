package com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.service;

import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.model.Pedido;
import com.perfulandia.perfulandiaSPA_EF1.gestionPedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    //BUSCAR TODOS LOS PEDIDOS Y POR ID
    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }
    public Pedido buscarPedidoPorId(Integer id) {
        return pedidoRepository.findById(id).get();
    }

    //GUARDAR, ACTUALIZAR Y ELIMINAR PEDIDO
    public Pedido save(Pedido pedido) { 
        return pedidoRepository.save(pedido);   
    }
    public void update(Pedido pedido) { 
        pedidoRepository.save(pedido);
    }
    public void delete(Integer idPedido) { 
        pedidoRepository.deleteById(idPedido);
    }
}
