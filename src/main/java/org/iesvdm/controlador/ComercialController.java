package org.iesvdm.controlador;

import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ComercialController {

    private final ComercialService comercialService;

    // Inyección automática por constructor
    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    // Listar todos los comerciales
    @GetMapping("/comerciales")
    public String listar(Model model) {
        List<Comercial> listaComerciales = comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);
        return "comerciales";  // nombre de la vista Thymeleaf
    }

    // Mostrar formulario para crear comercial
    @GetMapping("/comerciales/crear_comercial")
    public String crearComercial(Model model) {
        model.addAttribute("comercial", new Comercial());
        return "crear_comercial";
    }

    // Procesar formulario de creación
    @PostMapping("/comerciales/crear_comercial")
    public String crearComercial(@ModelAttribute("comercial") Comercial comercial) {
        comercialService.create(comercial);
        return "redirect:/comerciales";
    }

    // Ver detalle de un comercial
    @GetMapping("/comerciales/ver")
    public String verDetalleComercial(@RequestParam("id") long id, Model model) {
        var comercialOpt = comercialService.listAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (comercialOpt.isPresent()) {
            model.addAttribute("comercial", comercialOpt.get());
            return "ver_comercial";
        } else {
            return "redirect:/comerciales";
        }
    }

    // Mostrar formulario de edición
    @GetMapping("/comerciales/editar")
    public String mostrarFormularioEdicion(@RequestParam("id") long id, Model model) {
        var comercialOpt = comercialService.listAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (comercialOpt.isPresent()) {
            model.addAttribute("comercial", comercialOpt.get());
            return "editar_comercial";
        } else {
            return "redirect:/comerciales";
        }
    }

    // Procesar formulario de edición
    @PostMapping("/comerciales/editar")
    public String actualizarComercial(@RequestParam("id") int id, @ModelAttribute("comercial") Comercial comercial) {
        comercial.setId(id);
        comercialService.update(comercial);
        return "redirect:/comerciales";
    }

    // Mostrar confirmación de eliminación
    @GetMapping("/comerciales/eliminar")
    public String mostrarConfirmacionEliminarComercial(@RequestParam("id") long id, Model model) {
        var comercialOpt = comercialService.listAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        if (comercialOpt.isPresent()) {
            model.addAttribute("comercial", comercialOpt.get());
            model.addAttribute("comercialPuedeEliminarse", comercialService.canDelete(id));
            return "eliminar_comercial";
        } else {
            return "redirect:/comerciales";
        }
    }

    // Procesar eliminación
    @PostMapping("/comerciales/eliminar")
    public String eliminarComercial(@RequestParam("id") long id, Model model) {
        if (comercialService.canDelete(id)) {
            comercialService.delete(id);
        } else {
            model.addAttribute("listaComerciales", comercialService.listAll());
            model.addAttribute("error", "No se puede eliminar el comercial porque tiene pedidos asociados.");
            return "comerciales"; // o redirigir a lista con mensaje
        }
        return "redirect:/comerciales";
    }
}
