package org.iesvdm.service;

import org.iesvdm.dao.ComercialDAO;
import org.iesvdm.modelo.Comercial;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    private ComercialDAO comercialDAO;

    // Inyección automática por constructor (como en ClienteService)
    public ComercialService(ComercialDAO comercialDAO) {
        this.comercialDAO = comercialDAO;
    }

    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

    public Optional<Comercial> find(int id) {
        return comercialDAO.find(id);
    }

    public void create(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    public void update(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    public void delete(long id) {
        comercialDAO.delete(id);
    }
}