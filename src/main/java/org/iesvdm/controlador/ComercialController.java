package org.iesvdm.controlador;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
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
    ComercialService comercialService;

    @GetMapping("/comercial")
    public String listar(Model model) {

        List<Comercial> listaComercial = comercialService.listAll();
        model.addAttribute("listaComercial", listaComercial);

        return "comercial";
    }

    @GetMapping("/comercial/crear")
    public String crear(Model model) {

        Comercial comercial = new Comercial();

        model.addAttribute("comercial", comercial);

        return "/comercial/crear-comercial";
    }


    @PostMapping("/comercial/crear")
    public String submitCrear(@Valid @ModelAttribute("comercial") Comercial comercial,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/comercial/crear-comercial";
        }

        comercialService.newComercial(comercial);

        return "redirect:/comercial";
    }


    @GetMapping("/comercial/editar/{id}")
    public String editar(Model model, @PathVariable Integer id) {

        Comercial comercial = comercialService.findById(id);

        model.addAttribute("comercial", comercial);

        return "/comercial/editar-comercial";
    }

    @PostMapping("/comercial/editar/{id}")
    public String editarSubmit(@Valid @ModelAttribute("comercial") Comercial comercial,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/comercial/editar-comercial";
        }

        comercialService.replaceComercial(comercial);

        return "redirect:/comercial";
    }


    @PostMapping("/comercial/borrar/{id}")
    public RedirectView submitBorrar(@PathVariable Integer id) {

        comercialService.deleteComercial(id);

        return new RedirectView("/comercial");
    }

    @GetMapping("/comercial/detalles/{id}")
    public String infoComercial (Model model , @PathVariable int id){

        Comercial comercial = comercialService.findById(id);
        model.addAttribute("comercial", comercial);

        int cantidad = comercialService.getCantidadPedidos(id);
        model.addAttribute("cantidad" , cantidad);

        return "/comercial/detalle-comercial";
    }

}

