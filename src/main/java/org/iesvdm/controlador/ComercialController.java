package org.iesvdm.controlador;

import org.iesvdm.dto.EstadisticasComercialDTO;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.modelo.Pedido;
import org.iesvdm.service.ComercialService;
import org.iesvdm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ComercialController {

    @Autowired
    private ComercialService comercialService;

    @Autowired
    private PedidoService pedidoService;

    // Listar todos los comerciales
    @GetMapping("/comercial")
    public String listar(Model model) {
        List<Comercial> listaComercial = comercialService.listAll();
        model.addAttribute("listaComercial", listaComercial);
        return "/comercial/comerciales";
    }

    // Crear nuevo comercial
    @GetMapping("/comercial/crear")
    public String crear(Model model) {
        model.addAttribute("comercial", new Comercial());
        return "/comercial/crear-comercial";
    }

    @PostMapping("/comercial/crear")
    public RedirectView submitCrear(@ModelAttribute("comercial") Comercial comercial) {
        comercialService.newComercial(comercial);
        return new RedirectView("/comercial");
    }

    // Editar un comercial existente
    @GetMapping("/comercial/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {
        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);
        return "/comercial/editar-comercial";
    }

    @PostMapping("/comercial/editar/{id}")
    public RedirectView editarSubmit(@ModelAttribute("comercial") Comercial comercial) {
        comercialService.replaceComercial(comercial);
        return new RedirectView("/comercial");
    }

    // Eliminar un comercial
    @PostMapping("/comercial/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {
        comercialService.deleteComercial(id);
        return new RedirectView("/comercial");
    }

    // Ver detalles de un comercial y sus pedidos
    @GetMapping("/comercial/detalles/{id}")
    public String infoComercial(Model model, @PathVariable int id) {
        // Obtener comercial por ID
        Comercial comercial = comercialService.one(id);
        model.addAttribute("comercial", comercial);

        // Obtener los pedidos del comercial
        List<Pedido> pedidos = pedidoService.findPedidosByComercialId(id);

        // Calcular el total de pedidos
        float totalPedidos = pedidos.stream().mapToFloat(Pedido::getTotal).sum();

        // Calcular la media de los pedidos
        float mediaPedidos = pedidos.isEmpty() ? 0 : totalPedidos / pedidos.size();

        // Crear el DTO de estadísticas
        EstadisticasComercialDTO estadisticas = new EstadisticasComercialDTO(totalPedidos, mediaPedidos);

        // Pasar las estadísticas y pedidos al modelo
        model.addAttribute("estadisticas", estadisticas);
        model.addAttribute("pedidos", pedidos);

        return "/comercial/detalle-comercial";
    }
}
