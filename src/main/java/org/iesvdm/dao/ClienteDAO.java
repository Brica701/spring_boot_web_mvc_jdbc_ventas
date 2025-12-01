package org.iesvdm.dao;

import org.iesvdm.modelo.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {

    void create(Cliente cliente);

    List<Cliente> getAll();

    Optional<Cliente> find(int id);

    void update(Cliente cliente);

    void delete(long id);
}
