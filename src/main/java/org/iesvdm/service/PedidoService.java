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

    // LISTAR TODOS
    public List<Pedido> listAll() {
        return pedidoDAO.getAll();
    }

    // CREAR
    public void create(Pedido pedido) {
        pedidoDAO.create(pedido);
    }

    // BUSCAR POR ID
    public Optional<Pedido> find(int id) {
        return pedidoDAO.find(id);
    }

    // ACTUALIZAR
    public void update(Pedido pedido) {
        pedidoDAO.update(pedido);
    }

    // BORRAR
    public void delete(int id) {
        pedidoDAO.delete(id);
    }

    // FILTRAR POR CLIENTE
    public List<Pedido> filterByClienteId(int id) {
        return pedidoDAO.filterByClienteId(id);
    }

    // FILTRAR POR COMERCIAL
    public List<Pedido> filterByComercialId(int id) {
        return pedidoDAO.filterByComercialId(id);
    }
}
