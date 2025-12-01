package org.iesvdm.service;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.dao.ComercialDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    @Autowired
    private ComercialDAO comercialDAO;

    // Listar todos los comerciales
    public List<Comercial> listAll() {
        return comercialDAO.getAll();
    }

    // Obtener un comercial por ID
    public Comercial one(Integer id) {
        Optional<Comercial> optCom = comercialDAO.find(id);
        return optCom.orElse(null);
    }

    // Crear un comercial nuevo
    public void newComercial(Comercial comercial) {
        comercialDAO.create(comercial);
    }

    // Actualizar un comercial
    public void replaceComercial(Comercial comercial) {
        comercialDAO.update(comercial);
    }

    // Borrar un comercial
    public void deleteComercial(int id) {
        comercialDAO.delete(id);
    }

}
