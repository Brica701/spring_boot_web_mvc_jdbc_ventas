package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /clientes como
//prefijo.
//@RequestMapping("/clientes")
public class ClienteController {
	
	private ClienteService clienteService;
	
	//Se utiliza inyección automática por constructor del framework Spring.
	//Por tanto, se puede omitir la anotación Autowired
	//@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	//@RequestMapping(value = "/clientes", method = RequestMethod.GET)
	//equivalente a la siguiente anotación
	@GetMapping("/clientes") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
	public String listar(Model model) {
		
		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
				
		return "clientes";
		
	}

    @GetMapping("/clientes/crear_cliente")
    public String crearCliente(Model model) {
    	model.addAttribute("cliente", new Cliente());
    	return "crear_cliente";
    }

    @PostMapping("/clientes/crear_cliente")
    public String crearCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.create(cliente);
        return "redirect:/clientes";
    }

	// Procesar formulario de edición

	@GetMapping("/clientes/editar/{id}")
	public String mostrarFormularioEdicion(@PathVariable("id") long id, Model model) {
		var clienteOpt = clienteService.listAll()
				.stream()
				.filter(c -> c.getId() == id)
				.findFirst();
		if (clienteOpt.isPresent()) {
			model.addAttribute("cliente", clienteOpt.get()); // 🔑 Thymeleaf necesita esto
			return "editar_cliente";
		} else {
			return "redirect:/clientes"; // si no existe el cliente
		}
	}

	@PostMapping("/clientes/editar/{id}")
	public String actualizarCliente(@PathVariable("id") long id, @ModelAttribute("cliente") Cliente cliente) {
		cliente.setId(id);
		clienteService.update(cliente);
		return "redirect:/clientes";
	}

	// Ver detalle de un cliente
	@GetMapping("/clientes/{id}")
	public String verDetalleCliente(@PathVariable("id") long id, Model model) {
		var clienteOpt = clienteService.listAll()
				.stream()
				.filter(c -> c.getId() == id)
				.findFirst();
		if (clienteOpt.isPresent()) {
			model.addAttribute("cliente", clienteOpt.get());
			return "ver_cliente";
		} else {
			return "redirect:/clientes";
		}

	}

	@GetMapping("/clientes/eliminar/{id}")
	public String mostrarConfirmacionEliminar(@PathVariable("id") long id, Model model) {
		var clienteOpt = clienteService.listAll()
				.stream()
				.filter(c -> c.getId() == id)
				.findFirst();
		if (clienteOpt.isPresent()) {
			model.addAttribute("cliente", clienteOpt.get());

			// 🔑 Aquí agregamos la verificación
			model.addAttribute("clientePuedeEliminarse", clienteService.canDelete(id));

			return "eliminar_cliente"; // la vista de confirmación
		} else {
			return "redirect:/clientes"; // si no existe el cliente
		}
	}

	// Procesar eliminación
	@PostMapping("/clientes/eliminar/{id}")
	public String eliminarCliente(@PathVariable("id") long id, Model model) {
		if (clienteService.canDelete(id)) {
			clienteService.delete(id);
		} else {
			model.addAttribute("listaClientes", clienteService.listAll());
			model.addAttribute("error", "No se puede eliminar el cliente porque tiene pedidos asociados.");
			return "clientes"; // ahora Thymeleaf tiene todo lo que necesita
		}
		return "redirect:/clientes";
	}
}
