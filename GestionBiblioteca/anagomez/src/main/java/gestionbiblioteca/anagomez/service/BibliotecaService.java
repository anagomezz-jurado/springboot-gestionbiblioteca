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
    private final PrestamoRepository prestamoRepo;

    public BibliotecaService(LibroRepository libroRepo, SocioRepository socioRepo, PrestamoRepository prestamoRepo) {
        this.libroRepo = libroRepo;
        this.socioRepo = socioRepo;
        this.prestamoRepo = prestamoRepo;
    }

    // Libros
    public List<Libro> getAllLibros() {
        return libroRepo.findAll();
    }

    public Libro saveLibro(Libro libro) {
        return libroRepo.save(libro);
    }

    // Socios
    public List<Socio> getAllSocios() {
        return socioRepo.findAll();
    }

    public Socio saveSocio(Socio socio) {
        return socioRepo.save(socio);
    }

    // Prestamos
    public List<Prestamo> getAllPrestamos() {
        return prestamoRepo.findAll();
    }

    public Prestamo savePrestamo(Prestamo prestamo) {
        return prestamoRepo.save(prestamo);
    }

}
