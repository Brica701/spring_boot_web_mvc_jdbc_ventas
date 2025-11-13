package org.iesvdm.service;

import java.util.List;

import org.iesvdm.dao.ClienteDAO;
import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {


	@Autowired
	private ClienteDAO clienteDAO;
    @Autowired
    protected PedidoDAO pedidoDAO;
	
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

    public void delete(long id) {
    	clienteDAO.delete(id);
    }

    // Nuevo método: verifica si se puede borrar el cliente
    public boolean canDelete(long clienteId) {
        // Si el cliente NO tiene pedidos, se puede borrar
        return pedidoDAO.getAll().stream()
                .noneMatch(p -> p.getIdCliente() == clienteId);
    }

}
