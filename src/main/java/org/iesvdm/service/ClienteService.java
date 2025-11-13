package org.iesvdm.service;

import java.util.List;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.modelo.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
	}

    // Devuelve todos los clientes
    public List<Cliente> listAll() {
        return clienteDAO.getAll();
    }

    // Guarda o actualiza un cliente
    public void update(Cliente cliente) {
        clienteDAO.update(cliente);
    }
	
	public void create(Cliente cliente) {
        clienteDAO.create(cliente);
    }

}
