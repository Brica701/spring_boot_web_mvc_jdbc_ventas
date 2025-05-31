package org.iesvdm.controlador;

import jakarta.validation.Valid;
import org.iesvdm.dto.ClienteDTO;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	// Listar todos los clientes
	@GetMapping
	public String listarClientes(Model model) {
		List<Cliente> listaClientes = clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);
		return "clientes/listado";
	}

	// Mostrar formulario para crear cliente
	@GetMapping("/crear")
	public String mostrarFormularioCrear(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "clientes/formulario";
	}

	@PostMapping("/crear")
	public String crearCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
							   BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "clientes/formulario";
		}
		clienteService.newCliente(cliente);
		return "redirect:/clientes";
	}

	// Mostrar formulario para editar cliente
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
		Cliente cliente = clienteService.findById(id);
		if (cliente == null) return "redirect:/clientes";
		model.addAttribute("cliente", cliente);
		return "clientes/formulario";
	}

	// Procesar formulario de edición
	@PostMapping("/editar/{id}")
	public String editarCliente(@PathVariable Integer id,
								@Valid @ModelAttribute("cliente") Cliente cliente,
								BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "clientes/formulario";
		}
		cliente.setId(id);
		clienteService.replaceCliente(cliente);
		return "redirect:/clientes";
	}

	// Eliminar cliente
	@PostMapping("/borrar/{id}")
	public String borrarCliente(@PathVariable Integer id) {
		clienteService.deleteCliente(id);
		return "redirect:/clientes";
	}

	// Mostrar detalle del cliente con pedidos
	@GetMapping("/detalle/{id}")
	public String detalleCliente(@PathVariable Integer id, Model model) {
		Optional<ClienteDTO> clienteDTO = clienteService.obtenerDatosCliente(id);
		if (clienteDTO.isPresent()) {
			model.addAttribute("clienteDTO", clienteDTO.get());
			return "clientes/detalle";
		}
		return "redirect:/clientes";
	}

}
