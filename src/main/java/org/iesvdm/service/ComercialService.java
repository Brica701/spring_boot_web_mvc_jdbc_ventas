package org.iesvdm.service;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.repository.ComercialRepository;
import org.iesvdm.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComercialService {

    @Autowired
    private ComercialRepository comercialrepository;
    @Autowired
    private PedidoRepository pedidorepository;

    // Inyección automática por constructor (como en ClienteService)
    public ComercialService(ComercialRepository comercialrepository) {
        this.comercialrepository = comercialrepository;
    }

    public List<Comercial> listAll() {
        return comercialrepository.getAll();
    }

    public Optional<Comercial> find(int id) {
        return comercialrepository.find(id);
    }

    public void create(Comercial comercial) {
        comercialrepository.create(comercial);
    }

    public void update(Comercial comercial) {
        comercialrepository.update(comercial);
    }

    public void delete(long id) {
        comercialrepository.delete(id);
    }

    // Nuevo método: verifica si se puede borrar el comercial
    public boolean canDelete(long comercialId) {
        return pedidorepository.getAll().stream()
                .noneMatch(p -> p.getIdComercial() == comercialId);
    }
}