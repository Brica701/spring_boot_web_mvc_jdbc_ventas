package org.iesvdm.controlador;

import org.iesvdm.dao.PedidoDAO;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoDAO pedidoDAO;

    // LISTAR
    @GetMapping("/pedidos")
    public String listar(Model model) {
        List<Pedido> listaPedidos = pedidoService.listAll();
        model.addAttribute("listaPedidos", listaPedidos);
        return "pedidos";
    }

    // CREAR
    @GetMapping("/pedidos/crear")
    public String crear(Model model) {
        model.addAttribute("pedido", new Pedido());
        return "crear-pedido";
    }

    @PostMapping("/pedidos/crear")
    public String submitCrear(@ModelAttribute("pedido") Pedido pedido) {
        pedidoService.create(pedido);
        return "redirect:/pedidos";
    }

    // DETALLE
    @GetMapping("/pedidos/{id}")
    public String detalle(@PathVariable int id, Model model) {
        Pedido pedido = pedidoService.find(id).orElse(null);
        model.addAttribute("pedido", pedido);
        return "detalle-pedido";
    }

    // EDITAR
    @GetMapping("/pedidos/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Pedido pedido = pedidoService.find(id).orElse(null);
        model.addAttribute("pedido", pedido);
        return "editar-pedido";
    }

    @PostMapping("/pedidos/editar/{id}")
    public RedirectView editarSubmit(@PathVariable int id, @ModelAttribute("pedido") Pedido pedido) {
        pedido.setId(id);
        pedidoService.update(pedido);
        return new RedirectView("/pedidos");
    }

    // BORRAR
    @PostMapping("/pedidos/borrar/{id}")
    public RedirectView borrar(@PathVariable int id) {
        pedidoService.delete(id);
        return new RedirectView("/pedidos");
    }

}
