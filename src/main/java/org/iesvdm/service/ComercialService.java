package org.iesvdm.service;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.dao.ComercialDAOImpl;
import org.iesvdm.dao.PedidoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAOImpl comercialDAOImpl;
    @Autowired
    private PedidoDAOImpl pedidoDAOImpl;

    // Inyección automática por constructor (como en ClienteService)
    public ComercialService(ComercialDAOImpl comercialrepository) {
        this.comercialDAOImpl = comercialrepository;
    }

    public List<Comercial> listAll() {
        return comercialDAOImpl.getAll();
    }

    public Optional<Comercial> find(int id) {
        return comercialDAOImpl.find(id);
    }

    public void create(Comercial comercial) {
        comercialDAOImpl.create(comercial);
    }

    public void update(Comercial comercial) {
        comercialDAOImpl.update(comercial);
    }

    public void delete(long id) {
        comercialDAOImpl.delete(id);
    }

    // Nuevo método: verifica si se puede borrar el comercial
    public boolean canDelete(long comercialId) {
        return pedidoDAOImpl.getAll().stream()
                .noneMatch(p -> p.getIdComercial() == comercialId);
    }
}