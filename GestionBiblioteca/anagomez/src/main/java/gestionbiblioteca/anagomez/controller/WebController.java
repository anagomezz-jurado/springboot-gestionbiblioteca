package gestionbiblioteca.anagomez.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gestionbiblioteca.anagomez.excepciones.ResourceNotFoundException;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;
import jakarta.validation.Valid;

@Controller
public class WebController {

    private final BibliotecaService bibliotecaService;
    private final PrestamoService prestamoService;

    public WebController(BibliotecaService bibliotecaService, PrestamoService prestamoService) {
        this.bibliotecaService = bibliotecaService;
        this.prestamoService = prestamoService;
    }

    /* ========== MENÚ ========== */

    @GetMapping("/")
    public String menu() {
        return "menu";
    }

    /* ========== LIBROS ========== */

    @GetMapping("/libros")
    public String listarLibros(Model model) {
        model.addAttribute("libros", bibliotecaService.getAllLibros());
        return "libros";
    }

    /* ========== NUEVO LIBRO ========== */
    @GetMapping("/libros/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new Libro());
        return "nuevoLibro";
    }

    // Aquí reemplazas tu antiguo guardarLibro:
    @PostMapping("/libros/guardar")
    public String guardarLibro(@Valid @ModelAttribute Libro libro, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevoLibro";
        }
        bibliotecaService.saveLibro(libro);
        return "redirect:/libros";
    }

    /* ========== EDITAR LIBRO ========== */
    @GetMapping("/libros/editar/{id}")
    public String editarLibro(@PathVariable Long id, Model model) {
        Libro libro = bibliotecaService.obtenerLibro(id);
        model.addAttribute("libro", libro);
        return "editarLibro";
    }

    /* ========== ELIMINAR LIBRO ========== */
    @GetMapping("/libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        bibliotecaService.eliminarLibro(id);
        return "redirect:/libros";
    }

    @GetMapping("/libros/buscar")
    public String buscarLibros(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Libro> libros;
        if (query == null || query.isEmpty()) {
            libros = bibliotecaService.getAllLibros();
        } else {
            libros = bibliotecaService.buscarLibros(query);
        }
        model.addAttribute("libros", libros);
        model.addAttribute("query", query);
        return "libros";
    }

    /* ========== SOCIOS ========== */

    @GetMapping("/socios")
    public String listarSocios(Model model) {
        model.addAttribute("socios", bibliotecaService.getAllSocios());
        return "socios";
    }

    @GetMapping("/socios/nuevo")
    public String nuevoSocio(Model model) {
        model.addAttribute("socio", new Socio());
        return "nuevoSocio";
    }

    // Aquí reemplazas tu antiguo guardarSocio:
    @PostMapping("/socios/guardar")
    public String guardarSocio(@Valid @ModelAttribute Socio socio, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "nuevoSocio"; // vuelve al formulario mostrando errores
        }
        bibliotecaService.saveSocio(socio);
        return "redirect:/socios";
    }

    @GetMapping("/socios/editar/{id}")
    public String editarSocio(@PathVariable Long id, Model model) {
        model.addAttribute("socio", bibliotecaService.obtenerSocio(id));
        return "editarSocio";
    }

    @GetMapping("/socios/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id) {
        bibliotecaService.eliminarSocio(id); // AHORA SÍ BORRA SOCIOS
        return "redirect:/socios";
    }

    @GetMapping("/socios/buscar")
    public String buscarSocios(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Socio> socios;
        if (query == null || query.isEmpty()) {
            socios = bibliotecaService.getAllSocios();
        } else {
            socios = bibliotecaService.buscarSocios(query);
        }
        model.addAttribute("socios", socios);
        model.addAttribute("query", query);
        return "socios";
    }

    /* ========== PRÉSTAMOS ========== */

    @GetMapping("/prestamos")
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.listar());
        return "prestamos";
    }

    @GetMapping("/prestamos/nuevo")
    public String nuevoPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", bibliotecaService.getAllLibros());
        model.addAttribute("socios", bibliotecaService.getAllSocios());
        return "nuevoPrestamo";
    }

    @PostMapping("/prestamos/guardar")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo, Model model) {

        try {
            Libro libro = bibliotecaService.obtenerLibro(prestamo.getLibro().getId());
            Socio socio = bibliotecaService.obtenerSocio(prestamo.getSocio().getId());

            if (!prestamoService.puedePrestar(socio)) {
                throw new Exception("Este socio no puede realizar más préstamos.");
            }

            prestamoService.crearPrestamo(libro.getId(), socio.getId());
            return "redirect:/prestamos";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("libros", bibliotecaService.getAllLibros());
            model.addAttribute("socios", bibliotecaService.getAllSocios());
            return "nuevoPrestamo";
        }
    }

    @GetMapping("/prestamos/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable Long id) {
        prestamoService.eliminar(id);
        return "redirect:/prestamos";
    }

    @GetMapping("/prestamos/devolver/{id}")
    public String devolverPrestamo(@PathVariable Long id) {
        prestamoService.devolverPrestamo(id);
        return "redirect:/prestamos";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundWeb(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error"; // crea una plantilla error.html
    }

}
