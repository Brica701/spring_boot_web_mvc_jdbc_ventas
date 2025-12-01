package org.iesvdm.controlador;

import java.util.List;

import jakarta.validation.Valid;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ClienteService;
import org.iesvdm.dao.PedidoDAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoDAOImpl pedidoDAO;

    // ---------------------------
    // LISTAR
    // ---------------------------
    @GetMapping("/clientes")
    public String listar(Model model) {
        List<Cliente> listaClientes = clienteService.listAll();
        model.addAttribute("listaClientes", listaClientes);
        return "clientes";
    }

    // ---------------------------
    // CREAR
    // ---------------------------
    @GetMapping("/clientes/crear")
    public String crear(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "crear-clientes";
    }

    @PostMapping("/clientes/crear")
    public String submitCrear(@Valid @ModelAttribute("cliente") Cliente cliente,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return "crear-clientes";
        }

        clienteService.newCliente(cliente);
        return "redirect:/clientes";
    }

    // ---------------------------
    // DETALLE
    // ---------------------------
    @GetMapping("/clientes/{id}")
    public String detalle(@PathVariable Integer id, Model model) {

        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        List<Pedido> listaPedidos = pedidoDAO.filterByClienteId(id);
        model.addAttribute("listaPedidos", listaPedidos);

        return "detalle-cliente";
    }

    // ---------------------------
    // EDITAR
    // ---------------------------
    @GetMapping("/clientes/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);
        return "editar-cliente";
    }

    @PostMapping("/clientes/editar/{id}")
    public RedirectView editarSubmit(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.replaceCliente(cliente);
        return new RedirectView("/clientes");
    }

    // ---------------------------
    // BORRAR
    // ---------------------------
    @PostMapping("/clientes/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {
        clienteService.deleteCliente(id);
        return new RedirectView("/clientes");
    }

}
