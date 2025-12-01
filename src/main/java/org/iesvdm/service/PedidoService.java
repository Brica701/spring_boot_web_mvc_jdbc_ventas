package org.iesvdm.service;

import org.iesvdm.modelo.Pedido;
import org.iesvdm.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Crear un pedido
    public void create(Pedido pedido) {
        pedidoRepository.create(pedido);
    }

    // Obtener todos los pedidos
    public List<Pedido> getAll() {
        return pedidoRepository.getAll();
    }

    // Buscar un pedido por ID
    public Optional<Pedido> find(int id) {
        return pedidoRepository.find(id);
    }

    // Actualizar un pedido
    public void update(Pedido pedido) {
        pedidoRepository.update(pedido);
    }

    // Eliminar un pedido por ID
    public void delete(long id) {
        pedidoRepository.delete(id);
    }
}

