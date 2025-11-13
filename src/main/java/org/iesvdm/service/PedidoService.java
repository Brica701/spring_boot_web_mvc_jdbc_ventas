package org.iesvdm.service;

import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    // Crear un pedido
    public void create(Pedido pedido) {
        pedidoDAO.create(pedido);
    }

    // Obtener todos los pedidos
    public List<Pedido> getAll() {
        return pedidoDAO.getAll();
    }

    // Buscar un pedido por ID
    public Optional<Pedido> find(int id) {
        return pedidoDAO.find(id);
    }

    // Actualizar un pedido
    public void update(Pedido pedido) {
        pedidoDAO.update(pedido);
    }

    // Eliminar un pedido por ID
    public void delete(long id) {
        pedidoDAO.delete(id);
    }
}

