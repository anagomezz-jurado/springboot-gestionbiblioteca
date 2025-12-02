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

import gestionbiblioteca.anagomez.excepciones.MaxPrestamosException;
import gestionbiblioteca.anagomez.excepciones.ResourceNotFoundException;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;
import jakarta.validation.Valid;

@Controller // Controlador que devuelve vistas HTML
public class WebController {

    private final BibliotecaService bibliotecaService; // Servicio de libros y socios
    private final PrestamoService prestamoService; // Servicio de préstamos

    public WebController(BibliotecaService bibliotecaService, PrestamoService prestamoService) {
        this.bibliotecaService = bibliotecaService;
        this.prestamoService = prestamoService;
    }

    /* ================= MENÚ ================= */
    @GetMapping("/") // GET /
    public String menu() {
        return "menu"; // Devuelve menu.html
    }

    /* ================= LIBROS ================= */
    @GetMapping("/libros")
    public String listarLibros(Model model) {
        model.addAttribute("libros", bibliotecaService.getAllLibros()); // Pasa lista a la vista
        return "libro/libros"; // libro/libros.html
    }

    @GetMapping("/libros/nuevo")
    public String nuevoLibro(Model model) {
        model.addAttribute("libro", new Libro()); // Crea un libro vacío para el formulario
        return "libro/nuevoLibro"; // libro/nuevoLibro.html
    }

    @PostMapping("/libros/guardar")
    public String guardarLibro(@Valid @ModelAttribute Libro libro, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "libro/nuevoLibro"; // Si hay errores, vuelve al formulario
        }
        bibliotecaService.saveLibro(libro); // Guarda el libro
        return "redirect:/libros"; // Redirige a la lista
    }

    @GetMapping("/libros/editar/{id}")
    public String editarLibro(@PathVariable Long id, Model model) {
        Libro libro = bibliotecaService.obtenerLibro(id);
        model.addAttribute("libro", libro); // Pasa libro existente a la vista
        return "libro/editarLibro";
    }

    @GetMapping("/libros/eliminar/{id}")
    public String eliminarLibro(@PathVariable Long id) {
        bibliotecaService.eliminarLibro(id); // Borra libro
        return "redirect:/libros";
    }

    /* ================= SOCIOS ================= */
    @GetMapping("/socios")
    public String listarSocios(Model model) {
        model.addAttribute("socios", bibliotecaService.getAllSocios()); // Lista socios
        return "socio/socios";
    }

    @GetMapping("/socios/nuevo")
    public String nuevoSocio(Model model) {
        model.addAttribute("socio", new Socio()); // Crea socio vacío
        return "socio/nuevoSocio";
    }

    @PostMapping("/socios/guardar")
    public String guardarSocio(@Valid @ModelAttribute Socio socio, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "socio/nuevoSocio"; // Si hay errores vuelve al formulario
        }
        bibliotecaService.saveSocio(socio); // Guarda socio
        return "redirect:/socios"; // Redirige a la lista
    }

    @GetMapping("/socios/editar/{id}")
    public String editarSocio(@PathVariable Long id, Model model) {
        model.addAttribute("socio", bibliotecaService.obtenerSocio(id)); // Pasa socio existente
        return "socio/editarSocio";
    }

    @GetMapping("/socios/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id) {
        bibliotecaService.eliminarSocio(id); // Borra socio
        return "redirect:/socios";
    }

    /* ================= PRÉSTAMOS ================= */
    @GetMapping("/prestamos")
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.listar()); // Lista préstamos
        return "prestamo/prestamos";
    }

    @GetMapping("/prestamos/nuevo")
    public String nuevoPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", bibliotecaService.getAllLibros());
        model.addAttribute("socios", bibliotecaService.getAllSocios());
        return "prestamo/nuevoPrestamo";
    }

    @PostMapping("/prestamos/guardar")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo, Model model) {
        try {
            prestamoService.crearPrestamo(prestamo.getLibro().getId(), prestamo.getSocio().getId());
            return "redirect:/prestamos";
        } catch (MaxPrestamosException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("socios", bibliotecaService.getAllSocios());
            model.addAttribute("libros", bibliotecaService.getAllLibros());
            return "prestamo/nuevoPrestamo";
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
        return "error";
    }

}
