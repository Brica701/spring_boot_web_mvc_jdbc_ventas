package org.iesvdm.dao;

import org.iesvdm.modelo.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoDAO {
    void create(Pedido pedido);

    List<Pedido> getAll();

    Optional<Pedido> find(int id);

    void update(Pedido pedido);

    void delete(long id);
}
