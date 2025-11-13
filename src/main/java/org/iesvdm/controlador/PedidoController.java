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
}
