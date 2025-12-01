package org.iesvdm.service;

import org.iesvdm.modelo.Pedido;
import org.iesvdm.dao.PedidoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAOImpl pedidoDAOImpl;

    // Crear un pedido
    public void create(Pedido pedido) {
        pedidoDAOImpl.create(pedido);
    }

    // Obtener todos los pedidos
    public List<Pedido> getAll() {
        return pedidoDAOImpl.getAll();
    }

    // Buscar un pedido por ID
    public Optional<Pedido> find(int id) {
        return pedidoDAOImpl.find(id);
    }

    // Actualizar un pedido
    public void update(Pedido pedido) {
        pedidoDAOImpl.update(pedido);
    }

    // Eliminar un pedido por ID
    public void delete(long id) {
        pedidoDAOImpl.delete(id);
    }
}

