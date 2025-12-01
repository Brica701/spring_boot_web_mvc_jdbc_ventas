package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class ClienteController {

    private final ClienteService clienteService;

    // Inyección automática por constructor
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar todos los clientes
    @GetMapping("/clientes")
    public String listar(Model model) {
        List<Cliente> listaClientes = clienteService.listAll();
        model.addAttribute("listaClientes", listaClientes);
        return "clientes";
    }

    // Mostrar formulario para crear cliente
    @GetMapping("/clientes/crear_cliente")
    public String crearCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("tituloForm", "Crear Cliente");
        return "crear_cliente";
    }

    // Procesar formulario de creación con validación
    @PostMapping("/clientes/crear_cliente")
    public String crearCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "crear_cliente"; // Vuelve al formulario mostrando errores
        }
        clienteService.create(cliente);
        return "redirect:/clientes";
    }

    // Mostrar formulario de edición
    @GetMapping("/clientes/editar")
    public String mostrarFormularioEdicion(@RequestParam("id") long id, Model model) {
        var clienteOpt = clienteService.listAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            model.addAttribute("tituloForm", "Editar Cliente");
            return "editar_cliente";
        } else {
            return "redirect:/clientes";
        }
    }

    // Procesar formulario de edición con validación
    @PostMapping("/clientes/editar")
    public String actualizarCliente(@RequestParam("id") long id,
                                    @Valid @ModelAttribute("cliente") Cliente cliente,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editar_cliente"; // Vuelve al formulario con errores
        }
        cliente.setId(id);
        clienteService.update(cliente);
        return "redirect:/clientes";
    }

    // Ver detalle de un cliente
    @GetMapping("/clientes/ver")
    public String verDetalleCliente(@RequestParam("id") long id, Model model) {
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

    // Mostrar confirmación de eliminación
    @GetMapping("/clientes/eliminar")
    public String mostrarConfirmacionEliminar(@RequestParam("id") long id, Model model) {
        var clienteOpt = clienteService.listAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (clienteOpt.isPresent()) {
            model.addAttribute("cliente", clienteOpt.get());
            model.addAttribute("clientePuedeEliminarse", clienteService.canDelete(id));
            return "eliminar_cliente";
        } else {
            return "redirect:/clientes";
        }
    }

    // Procesar eliminación
    @PostMapping("/clientes/eliminar")
    public String eliminarCliente(@RequestParam("id") long id, Model model) {
        if (clienteService.canDelete(id)) {
            clienteService.delete(id);
        } else {
            model.addAttribute("listaClientes", clienteService.listAll());
            model.addAttribute("error", "No se puede eliminar el cliente porque tiene pedidos asociados.");
            return "clientes";
        }
        return "redirect:/clientes";
    }

}
