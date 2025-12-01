package org.iesvdm.dao;

import org.iesvdm.modelo.Comercial;

import java.util.List;
import java.util.Optional;

public interface ComercialDAO {

    void create(Comercial comercial);

    List<Comercial> getAll();

    Optional<Comercial> find(int id);

    void update(Comercial comercial);

    void delete(long id);
}
