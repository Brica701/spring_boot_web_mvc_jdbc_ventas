package org.iesvdm.controlador;

import java.util.List;

import jakarta.validation.Valid;
import org.iesvdm.dao.PedidoDAOImpl;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PedidoDAOImpl pedidoDAO;

	// LISTAR
	@GetMapping("/clientes")
	public String listar(Model model) {

		List<Cliente> listaClientes =  clienteService.listAll();
		model.addAttribute("listaClientes", listaClientes);

		return "clientes";

	}

	//CREAR
	@GetMapping("/clientes/crear")
	public String crear (Model model) {

		Cliente cliente = new Cliente();
		model.addAttribute("cliente",  cliente);

		return "crear-clientes";

	}

	@PostMapping("/clientes/crear")
	public RedirectView submitCrear(@Valid @ModelAttribute("cliente")Cliente cliente, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()){
			model.addAttribute("cliente",  cliente);
			List<Cliente> listaClientes =  this.clienteService.listAll();
			model.addAttribute("clientes", listaClientes);

			return "crear-cliente";
		}
		//clienteService.newCliente(cliente);
		 Cliente cliente1 =
		//return new RedirectView("/clientes") ;


    }

	@GetMapping("/clientes/{id}")
	public String detalle(Model model, @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		List<Pedido> listaPedidos = pedidoDAO.filterByComercialId(id);
		model.addAttribute("listaPedidos", listaPedidos);

		return "detalle-clientes";

	}

	@GetMapping("/clientes/editar/{id}")
	public String editar (Model model , @PathVariable Integer id) {

		Cliente cliente = clienteService.one(id);
		model.addAttribute("cliente", cliente);

		return "editar-clientes";

	}

	@PostMapping("/clientes/editar/{id}")
	public RedirectView editarSubmit(@ModelAttribute("cliente") Cliente cliente) {
		clienteService.replaceCliente(cliente);

		return new RedirectView("/clientes");
	}

	@PostMapping("/clientes/borrar/{id}")
	public RedirectView submitBorrar ( @PathVariable Integer id) {
		clienteService.deleteCliente(id);

		return new RedirectView("/clientes") ;
	}

}