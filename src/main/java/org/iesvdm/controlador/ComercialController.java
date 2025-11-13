package org.iesvdm.controlador;

import org.iesvdm.modelo.Cliente;
import org.iesvdm.modelo.Comercial;
import org.iesvdm.service.ComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//Se puede fijar ruta base de las peticiones de este controlador.
//Los mappings de los métodos tendrían este valor /comerciales como
//prefijo.
//@RequestMapping("/comerciales")
public class ComercialController {

    private ComercialService comercialService;

    //Se utiliza inyección automática por constructor del framework Spring.
    //Por tanto, se puede omitir la anotación Autowired
    //@Autowired
    public ComercialController(ComercialService comercialService) {
        this.comercialService = comercialService;
    }

    //@RequestMapping(value = "/comerciales", method = RequestMethod.GET)
    //equivalente a la siguiente anotación
    @GetMapping("/comerciales") //Al no tener ruta base para el controlador, cada método tiene que tener la ruta completa
    public String listar(Model model) {

        List<Comercial> listaComerciales = comercialService.listAll();
        model.addAttribute("listaComerciales", listaComerciales);

        return "comerciales";  // nombre de la vista Thymeleaf (comerciales.html)

    }

    @GetMapping("/comerciales/crear_comercial")
    public String crearComercial(Model model) {
        model.addAttribute("comercial", new Comercial());
        return "crear_comercial";
    }

    @PostMapping("/comerciales/crear_comercial")
    public String crearCliente(@ModelAttribute("comercial") Comercial comercial) {
        comercialService.create(comercial);
        return "redirect:/comerciales";
    }

}