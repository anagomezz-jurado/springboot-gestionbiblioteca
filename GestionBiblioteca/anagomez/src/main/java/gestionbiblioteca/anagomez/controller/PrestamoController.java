package gestionbiblioteca.anagomez.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.service.PrestamoService;
import gestionbiblioteca.anagomez.service.LibroService;
import gestionbiblioteca.anagomez.service.SocioService;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final LibroService libroService;
    private final SocioService socioService;

    public PrestamoController(PrestamoService prestamoService, LibroService libroService, SocioService socioService) {
        this.prestamoService = prestamoService;
        this.libroService = libroService;
        this.socioService = socioService;
    }

    @GetMapping
    public String listarPrestamos(Model model) {
        List<Prestamo> prestamos = prestamoService.listarPrestamos();
        model.addAttribute("prestamos", prestamos);
        return "prestamos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioPrestamo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", libroService.listarLibros());
        model.addAttribute("socios", socioService.listarSocios());
        return "nuevoPrestamo"; // Debe coincidir exactamente con el nombre del archivo
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(@ModelAttribute Prestamo prestamo) {
        prestamo.setFechaInicio(LocalDateTime.now());
        prestamo.setEstado(Prestamo.Estado.ACTIVO);
        prestamoService.guardarPrestamo(prestamo);
        return "redirect:/prestamos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable Long id) {
        prestamoService.eliminarPrestamo(id);
        return "redirect:/prestamos";
    }
}
