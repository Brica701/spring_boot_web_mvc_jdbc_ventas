package org.iesvdm.controlador;

import java.util.List;

import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PedidoController {

    private final PedidoService pedidoService;

    // Inyección automática por constructor
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Listar todos los pedidos
    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        List<Pedido> listaPedidos = pedidoService.getAll();
        model.addAttribute("listaPedidos", listaPedidos);
        return "pedidos";
    }

    // Mostrar formulario para crear pedido
    @GetMapping("/pedidos/crear_pedido")
    public String mostrarFormulario(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "crear_pedido";
    }

    // Procesar formulario de creación
    @PostMapping("/pedidos/crear_pedido")
    public String crearPedido(@ModelAttribute("pedido") Pedido pedido) {
        pedidoService.create(pedido);
        return "redirect:/pedidos";
    }

    // Mostrar formulario de edición
    @GetMapping("/pedidos/editar")
    public String mostrarFormularioEdicion(@RequestParam("id") int id, Model model) {
        var pedidoOpt = pedidoService.find(id);
        if (pedidoOpt.isPresent()) {
            model.addAttribute("pedido", pedidoOpt.get());
            return "editar_pedido";
        } else {
            return "redirect:/pedidos";
        }
    }

    // Procesar formulario de edición
    @PostMapping("/pedidos/editar")
    public String actualizarPedido(@RequestParam("id") int id, @ModelAttribute("pedido") Pedido pedido) {
        pedido.setId(id);
        pedidoService.update(pedido);
        return "redirect:/pedidos";
    }

    // Mostrar confirmación de eliminación
    @GetMapping("/pedidos/eliminar")
    public String mostrarConfirmacionEliminar(@RequestParam("id") int id, Model model) {
        var pedidoOpt = pedidoService.find(id);
        if (pedidoOpt.isPresent()) {
            model.addAttribute("pedido", pedidoOpt.get());
            return "eliminar_pedido";
        } else {
            return "redirect:/pedidos";
        }
    }

    // Procesar eliminación
    @PostMapping("/pedidos/eliminar")
    public String eliminarPedido(@RequestParam("id") int id) {
        pedidoService.delete(id);
        return "redirect:/pedidos";
    }

}
