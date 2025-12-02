package gestionbiblioteca.anagomez.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gestionbiblioteca.anagomez.excepciones.MaxPrestamosException;
import gestionbiblioteca.anagomez.excepciones.ResourceNotFoundException;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.service.BibliotecaService;
import gestionbiblioteca.anagomez.service.PrestamoService;
import jakarta.validation.Valid;

@RestController // Indica que este controlador devuelve JSON en las respuestas
@RequestMapping("/api") // Todas las rutas comienzan con /api
public class BibliotecaRestController {

    private final BibliotecaService service; // Servicio principal para libros y socios
    private final PrestamoService prestamoService; // Servicio de préstamos

    // Constructor con inyección de dependencias
    public BibliotecaRestController(BibliotecaService service, PrestamoService prestamoService) {
        this.service = service;
        this.prestamoService = prestamoService;
    }

    /* ======================= LIBROS ======================= */

    @GetMapping("/libros") // GET /api/libros
    public List<Libro> getAllLibros() {
        return service.getAllLibros(); // Devuelve todos los libros en formato JSON
    }

    @GetMapping("/libros/{id}") // GET /api/libros/1
    public Libro getLibro(@PathVariable Long id) {
        return service.obtenerLibro(id); // Devuelve un libro por ID
    }

    @PostMapping("/libros") // POST /api/libros
    public ResponseEntity<?> crearLibro(@Valid @RequestBody Libro libro, BindingResult result) {
        // Valida los campos del libro (@NotNull, @Size, etc.)
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            // Recorre todos los errores y los devuelve como JSON
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores); // 400 Bad Request
        }
        Libro nuevo = service.saveLibro(libro); // Guarda el libro
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201 Created con el libro
    }

    @PutMapping("/libros/{id}") // PUT /api/libros/1
    public Libro actualizarLibro(@PathVariable Long id, @RequestBody Libro libro) {
        Libro existing = service.obtenerLibro(id); // Obtiene el libro existente
        existing.setTitulo(libro.getTitulo()); // Actualiza título
        existing.setAutor(libro.getAutor()); // Actualiza autor
        return service.saveLibro(existing); // Guarda cambios
    }

    @DeleteMapping("/libros/{id}") // DELETE /api/libros/1
    public void borrarLibro(@PathVariable Long id) {
        service.eliminarLibro(id); // Elimina el libro
    }

    /* ======================= SOCIOS ======================= */

    @GetMapping("/socios") // GET /api/socios
    public List<Socio> getAllSocios() {
        return service.getAllSocios(); // Devuelve todos los socios
    }

    @GetMapping("/socios/{id}") // GET /api/socios/1
    public Socio getSocio(@PathVariable Long id) {
        return service.obtenerSocio(id); // Devuelve un socio por ID
    }

    @PostMapping("/socios") // POST /api/socios
    public ResponseEntity<?> crearSocio(@Valid @RequestBody Socio socio, BindingResult result) {
        // Valida campos del socio
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores); // 400 Bad Request
        }
        Socio nuevo = service.saveSocio(socio); // Guarda el socio
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201 Created
    }

    @PutMapping("/socios/{id}") // PUT /api/socios/1
    public Socio actualizarSocio(@PathVariable Long id, @RequestBody Socio socio) {
        Socio existing = service.obtenerSocio(id); // Obtiene el socio existente
        existing.setNombre(socio.getNombre()); // Actualiza nombre
        existing.setEmail(socio.getEmail()); // Actualiza email
        return service.saveSocio(existing); // Guarda cambios
    }

    @DeleteMapping("/socios/{id}") // DELETE /api/socios/1
    public void borrarSocio(@PathVariable Long id) {
        prestamoService.eliminar(id); // Elimina préstamos asociados y/o socio
    }

    /* ===================== PRÉSTAMOS ===================== */

    @GetMapping("/prestamos") // GET /api/prestamos
    public List<Prestamo> getAllPrestamos() {
        return prestamoService.listar(); // Lista todos los préstamos
    }

    @GetMapping("/prestamos/{id}") // GET /api/prestamos/1
    public Prestamo getPrestamo(@PathVariable Long id) {
        return prestamoService.obtener(id); // Devuelve un préstamo específico
    }

    @PostMapping("/prestamos")
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo nuevo = prestamoService.crearPrestamo(
                    prestamo.getLibro().getId(),
                    prestamo.getSocio().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (MaxPrestamosException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/prestamos/devolver/{id}") // PUT /api/prestamos/devolver/1
    public Prestamo devolverPrestamo(@PathVariable Long id) {
        return prestamoService.devolverPrestamo(id); // Marca el préstamo como devuelto
    }

    @DeleteMapping("/prestamos/{id}") // DELETE /api/prestamos/1
    public void borrarPrestamo(@PathVariable Long id) {
        prestamoService.eliminar(id); // Elimina un préstamo
    }

    /* ===================== MANEJO DE ERRORES ===================== */

    @ExceptionHandler(ResourceNotFoundException.class) // Captura excepción personalizada
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        // Devuelve JSON con error y status 404
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }
}
