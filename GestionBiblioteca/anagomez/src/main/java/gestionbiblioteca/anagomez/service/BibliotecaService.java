package gestionbiblioteca.anagomez.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import gestionbiblioteca.anagomez.excepciones.ResourceNotFoundException;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.repository.LibroRepository;
import gestionbiblioteca.anagomez.repository.SocioRepository;
import gestionbiblioteca.anagomez.repository.PrestamoRepository;

@Service // Marca la clase como servicio de Spring (componente de lógica de negocio)
public class BibliotecaService {

    private final LibroRepository libroRepo; // Repositorio de libros
    private final SocioRepository socioRepo; // Repositorio de socios

    // Constructor para inyección de dependencias
    public BibliotecaService(LibroRepository libroRepo, SocioRepository socioRepo) {
        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
    }

    /* ========================== LIBROS ========================== */

    // Obtener todos los libros
    public List<Libro> getAllLibros() {
        return libroRepo.findAll();
    }

    // Guardar o actualizar un libro
    public Libro saveLibro(Libro libro) {
        return libroRepo.save(libro);
    }

    // Obtener un libro por id, lanza excepción si no existe
    public Libro obtenerLibro(Long id) {
        return libroRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con id: " + id));
    }

    // Eliminar libro por id
    public void eliminarLibro(Long id) {
        libroRepo.deleteById(id);
    }

    // Buscar libros por título o autor (ignore case)
    public List<Libro> buscarLibros(String query) {
        return libroRepo.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(query, query);
    }

    /* ========================== SOCIOS ========================== */

    // Obtener todos los socios
    public List<Socio> getAllSocios() {
        return socioRepo.findAll();
    }

    // Guardar o actualizar un socio
    public Socio saveSocio(Socio socio) {
        return socioRepo.save(socio);
    }

    // Obtener socio por id, lanza excepción si no existe
    public Socio obtenerSocio(Long id) {
        return socioRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con id: " + id));
    }

    // Eliminar socio por id
    public void eliminarSocio(Long id) {
        socioRepo.deleteById(id);
    }

    // Buscar socios por nombre o email (ignore case)
    public List<Socio> buscarSocios(String query) {
        return socioRepo.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }

}
