package gestionbiblioteca.anagomez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;

@Controller
public class WebController {

    private final BibliotecaService service;

    public WebController(BibliotecaService service) {
        this.service = service;
    }

    // Página principal con el menú
    @GetMapping("/")
    public String menu() {
        return "menu";
    }

    // Listar libros
    @GetMapping("/libros")
    public String listarLibros(Model model) {
        model.addAttribute("libros", service.getAllLibros());
        return "libros";
    }

    // Listar socios
    @GetMapping("/socios")
    public String listarSocios(Model model) {
        model.addAttribute("socios", service.getAllSocios());
        return "socios";
    }

}
