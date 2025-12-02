package gestionbiblioteca.anagomez.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;

@RestController
@RequestMapping("/api")
public class BibliotecaRestController {

    private final BibliotecaService service;
    private final PrestamoService prestamoService;

    public BibliotecaRestController(BibliotecaService service, PrestamoService prestamoService) {
        this.service = service;
        this.prestamoService = prestamoService;
    }

    /* ======================= LIBROS ======================= */
    @GetMapping("/libros")
    public List<Libro> getAllLibros() {
        return service.getAllLibros();
    }

    @PostMapping("/libros")
    public Libro crearLibro(@RequestBody Libro libro) {
        return service.saveLibro(libro);
    }

    /* ======================= SOCIOS ======================= */
    @GetMapping("/socios")
    public List<Socio> getAllSocios() {
        return service.getAllSocios();
    }

    @PostMapping("/socios")
    public Socio crearSocio(@RequestBody Socio socio) {
        return service.saveSocio(socio);
    }

    /* ===================== PRESTAMOS ===================== */
    @GetMapping("/prestamos")
    public List<Prestamo> getAllPrestamos() {
        return prestamoService.listar();
    }

    @PostMapping("/prestamos")
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            return prestamoService.crearPrestamo(
                    prestamo.getLibro().getId(),
                    prestamo.getSocio().getId());

        } catch (Exception e) {
            throw new RuntimeException("Error al crear préstamo: " + e.getMessage());
        }
    }
}
