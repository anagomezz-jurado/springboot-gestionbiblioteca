package gestionbiblioteca.anagomez.service;

import java.util.List;

import org.springframework.stereotype.Service;

import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.modelo.Socio;
import gestionbiblioteca.anagomez.modelo.Prestamo;
import gestionbiblioteca.anagomez.repository.LibroRepository;
import gestionbiblioteca.anagomez.repository.SocioRepository;
import gestionbiblioteca.anagomez.repository.PrestamoRepository;

@Service
public class BibliotecaService {

    private final LibroRepository libroRepo;
    private final SocioRepository socioRepo;

    public BibliotecaService(LibroRepository libroRepo, SocioRepository socioRepo) {
        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
    }

    /* ======================= LIBROS ======================= */

    public List<Libro> getAllLibros() {
        return libroRepo.findAll();
    }

    public Libro saveLibro(Libro libro) {
        return libroRepo.save(libro);
    }

    /* ======================= SOCIOS ======================= */

    public List<Socio> getAllSocios() {
        return socioRepo.findAll();
    }

    public Socio saveSocio(Socio socio) {
        return socioRepo.save(socio);
    }

    public Socio obtenerSocio(Long id) {
        return socioRepo.findById(id).orElseThrow();
    }

    public Libro obtenerLibro(Long id) {
        return libroRepo.findById(id).orElseThrow();
    }
}
