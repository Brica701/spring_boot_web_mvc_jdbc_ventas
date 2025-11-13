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
        return "pedidos"; // Thymeleaf: pedidos.html
    }

    // Mostrar formulario para crear pedido
    @GetMapping("/pedidos/crear_pedido")
    public String mostrarFormulario(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "crear_pedido"; // Thymeleaf: crear_pedido.html
    }

    // Procesar formulario de creación
    @PostMapping("/pedidos/crear_pedido")
    public String crearPedido(@ModelAttribute("pedido") Pedido pedido) {
        pedidoService.create(pedido);
        return "redirect:/pedidos";
    }

    //Mostrar el editar pedido
    @GetMapping("/pedidos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") int id, Model model) {
        var pedidoOpt = pedidoService.find(id);
        if (pedidoOpt.isPresent()) {
            model.addAttribute("pedido", pedidoOpt.get());
            return "editar_pedido"; // 👈 aquí coincide con tu archivo HTML
        } else {
            return "redirect:/pedidos";
        }
    }

    // Procesar formulario de edición
    @PostMapping("/pedidos/editar/{id}")
    public String actualizarPedido(@PathVariable("id") int id, @ModelAttribute("pedido") Pedido pedido) {
        pedido.setId(id);
        pedidoService.update(pedido);
        return "redirect:/pedidos";
    }

    // Mostrar formulario de confirmación
    @GetMapping("/pedidos/eliminar/{id}")
    public String mostrarConfirmacionEliminar(@PathVariable("id") int id, Model model) {
        var pedidoOpt = pedidoService.find(id);
        if (pedidoOpt.isPresent()) {
            model.addAttribute("pedido", pedidoOpt.get());
            return "eliminar_pedido";
        } else {
            return "redirect:/pedidos";
        }
    }

    // Procesar la eliminación (POST)
    @PostMapping("/pedidos/eliminar/{id}")
    public String eliminarPedido(@PathVariable("id") int id) {
        pedidoService.delete(id);
        return "redirect:/pedidos";
    }

}
