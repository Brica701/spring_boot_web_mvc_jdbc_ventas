package org.iesvdm.controlador;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.ClienteService;
import org.iesvdm.dao.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Comparator;
import java.util.List;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PedidoDAO pedidoDAO;

    // LISTAR
    @GetMapping("/comerciales")
    public String listar(Model model) {
        List<Comercial> listaComerciales = comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);
        return "comerciales";
    }

    // CREAR
    @GetMapping("/comerciales/crear")
    public String crear(Model model) {
        Comercial comercial = new Comercial();
        model.addAttribute("comercial", comercial);
        return "crear-comercial";
    }

    @PostMapping("/comerciales/crear")
    public String submitCrear(@Valid @ModelAttribute("comercial") Comercial comercial,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("comercial", comercial);
            return "crear-comercial";
        }
        comercialService.newComercial(comercial);
        return "redirect:/comerciales";
    }

    // DETALLE
    @GetMapping("/comerciales/{id}")
    public String detalle(@PathVariable Integer id, Model model) {
        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        // Lista de pedidos asociados al comercial
        List<Pedido> listaPedidos = pedidoDAO.filterByComercialId(id);
        model.addAttribute("listaPedidos", listaPedidos);

        // Cliente relacionado (si aplica)
        Cliente cliente = clienteService.one(id);
        model.addAttribute("cliente", cliente);

        // Pedido máximo y mínimo por total
        Pedido maxPedido = listaPedidos.stream()
                .max(Comparator.comparingDouble(Pedido::getTotal))
                .orElse(null);
        model.addAttribute("maxPedido", maxPedido);

        Pedido minPedido = listaPedidos.stream()
                .min(Comparator.comparingDouble(Pedido::getTotal))
                .orElse(null);
        model.addAttribute("minPedido", minPedido);

        return "detalle-comercial";
    }

    // EDITAR
    @GetMapping("/comerciales/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {
        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);
        return "editar-comercial";
    }

    @PostMapping("/comerciales/editar/{id}")
    public RedirectView editarSubmit(@ModelAttribute("comercial") Comercial comercial) {
        comercialService.replaceComercial(comercial);
        return new RedirectView("/comerciales");
    }

    // BORRAR
    @PostMapping("/comerciales/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {
        comercialService.deleteComercial(id);
        return new RedirectView("/comerciales");
    }

}
