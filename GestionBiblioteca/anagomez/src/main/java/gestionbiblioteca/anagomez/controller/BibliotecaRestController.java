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

    @GetMapping("/libros/{id}")
    public Libro getLibro(@PathVariable Long id) {
        return service.obtenerLibro(id);
    }

    @PostMapping("/libros")
    public Libro crearLibro(@RequestBody Libro libro) {
        return service.saveLibro(libro);
    }

    @PutMapping("/libros/{id}")
    public Libro actualizarLibro(@PathVariable Long id, @RequestBody Libro libro) {
        Libro existing = service.obtenerLibro(id);
        existing.setTitulo(libro.getTitulo());
        existing.setAutor(libro.getAutor());
        return service.saveLibro(existing);
    }

    @DeleteMapping("/libros/{id}")
    public void borrarLibro(@PathVariable Long id) {
        service.eliminarLibro(id);
    }

    /* ======================= SOCIOS ======================= */
    @GetMapping("/socios")
    public List<Socio> getAllSocios() {
        return service.getAllSocios();
    }

    @GetMapping("/socios/{id}")
    public Socio getSocio(@PathVariable Long id) {
        return service.obtenerSocio(id);
    }

    @PostMapping("/socios")
    public Socio crearSocio(@RequestBody Socio socio) {
        return service.saveSocio(socio);
    }

    @PutMapping("/socios/{id}")
    public Socio actualizarSocio(@PathVariable Long id, @RequestBody Socio socio) {
        Socio existing = service.obtenerSocio(id);
        existing.setNombre(socio.getNombre());
        existing.setEmail(socio.getEmail());
        return service.saveSocio(existing);
    }

    @DeleteMapping("/socios/{id}")
    public void borrarSocio(@PathVariable Long id) {
        prestamoService.eliminar(id);
    }

    /* ===================== PRESTAMOS ===================== */
    @GetMapping("/prestamos")
    public List<Prestamo> getAllPrestamos() {
        return prestamoService.listar();
    }

    @GetMapping("/prestamos/{id}")
    public Prestamo getPrestamo(@PathVariable Long id) {
        return prestamoService.obtener(id);
    }

    @PostMapping("/prestamos")
    public Prestamo crearPrestamo(@RequestBody Prestamo prestamo) {
        Socio socio = service.obtenerSocio(prestamo.getSocio().getId());

        if (!prestamoService.puedePrestar(socio)) {
            throw new RuntimeException(
                    "El socio no puede realizar préstamos: máximo 3 activos o tiene penalización vigente.");
        }

        return prestamoService.crearPrestamo(prestamo.getLibro().getId(), socio.getId());
    }

    @PutMapping("/prestamos/devolver/{id}")
    public Prestamo devolverPrestamo(@PathVariable Long id) {
        return prestamoService.devolverPrestamo(id);
    }

    @DeleteMapping("/prestamos/{id}")
    public void borrarPrestamo(@PathVariable Long id) {
        prestamoService.eliminar(id);
    }

}
