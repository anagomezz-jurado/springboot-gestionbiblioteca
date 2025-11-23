package gestionbiblioteca.anagomez.service;

import java.util.List;
import org.springframework.stereotype.Service;
import gestionbiblioteca.anagomez.modelo.Libro;
import gestionbiblioteca.anagomez.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }
}
