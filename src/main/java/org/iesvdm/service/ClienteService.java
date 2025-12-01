package org.iesvdm.service;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.repository.ClienteRepository;
import org.iesvdm.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {


	@Autowired
	private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteService(ClienteRepository clienteDAO) {
        this.clienteRepository = clienteRepository;
	}

    // Devuelve todos los clientes
    public List<Cliente> listAll() {
        return clienteRepository.getAll();
    }

    // Guarda o actualiza un cliente
    public void update(Cliente cliente) {
        clienteRepository.update(cliente);
    }
	
	public void create(Cliente cliente) {
        clienteRepository.create(cliente);
    }

    public void delete(long id) {
        clienteRepository.delete(id);
    }

    // Nuevo método: verifica si se puede borrar el cliente
    public boolean canDelete(long clienteId) {
        // Si el cliente NO tiene pedidos, se puede borrar
        return pedidoRepository.getAll().stream()
                .noneMatch(p -> p.getIdCliente() == clienteId);
    }

}
