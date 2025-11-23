package gestionbiblioteca.anagomez.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.service.BibliotecaService;

@RestController
@RequestMapping("/api")
public class BibliotecaController {

    private final BibliotecaService service;

    public BibliotecaController(BibliotecaService service) {
        this.service = service;
    }

    // Libros
    @GetMapping("/libros")
    public List<Libro> getAllLibros() {
        return service.getAllLibros();
    }

    @PostMapping("/libros")
    public Libro crearLibro(@RequestBody Libro libro) {
        return service.saveLibro(libro);
    }

    // Socios
    @GetMapping("/socios")
    public List<Socio> getAllSocios() {
        return service.getAllSocios();
    }

    @PostMapping("/socios")
    public Socio crearSocio(@RequestBody Socio socio) {
        return service.saveSocio(socio);
    }

    // Prestamos
    @GetMapping("/prestamos")
    public List<Prestamo> getAllPrestamos() {
        return service.getAllPrestamos();
    }

    @PostMapping("/prestamos")
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        return service.savePrestamo(prestamo);
    }
}
