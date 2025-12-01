package org.iesvdm.service;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.dao.ClienteDAOImpl;
import org.iesvdm.dao.PedidoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {


	@Autowired
	private ClienteDAOImpl clienteDAOImpl;
    @Autowired
    private PedidoDAOImpl pedidoDAOImpl;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteDAOImpl clienteDAO) {
        this.clienteDAOImpl = clienteDAOImpl;
	}

    // Devuelve todos los clientes
    public List<Cliente> listAll() {
        return clienteDAOImpl.getAll();
    }

    // Guarda o actualiza un cliente
    public void update(Cliente cliente) {
        clienteDAOImpl.update(cliente);
    }
	
	public void create(Cliente cliente) {
        clienteDAOImpl.create(cliente);
    }

    public void delete(long id) {
        clienteDAOImpl.delete(id);
    }

    // Nuevo método: verifica si se puede borrar el cliente
    public boolean canDelete(long clienteId) {
        // Si el cliente NO tiene pedidos, se puede borrar
        return pedidoDAOImpl.getAll().stream()
                .noneMatch(p -> p.getIdCliente() == clienteId);
    }

}
