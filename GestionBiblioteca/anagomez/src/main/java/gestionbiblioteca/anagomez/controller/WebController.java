package gestionbiblioteca.anagomez.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;

@Controller
public class WebController {

    private final BibliotecaService bibliotecaService;
    private final PrestamoService prestamoService;

    public WebController(
            BibliotecaService bibliotecaService,
            PrestamoService prestamoService) {

        this.bibliotecaService = bibliotecaService;
        this.prestamoService = prestamoService;
    }

    /* ========== MENÚ PRINCIPAL ========== */
    @GetMapping("/")
    public String menu() {
        return "menu";
    }

    /* ========== LISTADOS WEB ========== */

    @GetMapping("/libros")
    public String listarLibros(Model model) {
        model.addAttribute("libros", bibliotecaService.getAllLibros());
        return "libros";
    }

    @GetMapping("/socios")
    public String listarSocios(Model model) {
        model.addAttribute("socios", bibliotecaService.getAllSocios());
        return "socios";
    }

    @GetMapping("/prestamos")
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.listar());
        return "prestamos";
    }

    /* ========== NUEVO PRÉSTAMO ========== */

    @GetMapping("/prestamos/nuevo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", bibliotecaService.getAllLibros());
        model.addAttribute("socios", bibliotecaService.getAllSocios());
        return "nuevoPrestamo";
    }

    @PostMapping("/prestamos/guardar")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo, Model model) {
        try {
            // Obtener las entidades reales a partir de los IDs
            Libro libro = bibliotecaService.obtenerLibro(prestamo.getLibro().getId());
            Socio socio = bibliotecaService.obtenerSocio(prestamo.getSocio().getId());

            prestamo.setLibro(libro);
            prestamo.setSocio(socio);

            // Guardar préstamo usando el servicio
            prestamoService.crearPrestamo(socio.getId(), libro.getId());

            return "redirect:/prestamos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("prestamo", new Prestamo());
            model.addAttribute("libros", bibliotecaService.getAllLibros());
            model.addAttribute("socios", bibliotecaService.getAllSocios());
            return "nuevoPrestamo";
        }
    }

    /* ========== ELIMINAR PRÉSTAMO ========== */

    @GetMapping("/prestamos/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable Long id) {
        prestamoService.eliminar(id);
        return "redirect:/prestamos";
    }
}
